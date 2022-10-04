package io.mysearch.businesssearch.services;

import io.mysearch.businesssearch.clients.BusinessSearchClient;
import io.mysearch.businesssearch.clients.FeignClientErrorCode;
import io.mysearch.businesssearch.clients.FeignClientException;
import io.mysearch.businesssearch.errors.BusinessNotFoundException;
import io.mysearch.businesssearch.models.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Queries the remote business search service using the feign client and adapts the response for client applications.
 * <p>
 * The remote service returns a map of days, where each day has its associated opening intervals.
 * This service enumerates through the working days in order, then groups together days which have the same intervals.
 * The result will be a list of groups, each group containing a set of days paired with a set of intervals.
 * <p>
 * The complexity of the algorithm O(N * logN) where N is the maximum number of intervals that a day may have.
 * This is because for each two subsequent days, intervals need to be matched which requires searching each interval
 * in a set of intervals, thus the N * logN.
 */
@Service
@RequiredArgsConstructor
public class WebAppBusinessSearchServiceImpl implements WebAppBusinessSearchService {

  private final BusinessSearchClient businessSearchClient;

  @Override
  public WebAppBusinessResponse search(String id) {
    Validate.notEmpty(id, "id is null or empty.");

    final var business = getBusiness(id);

    final var openingDaysList = getOpeningDaysList(business);

    return WebAppBusinessResponse.builder()
      .name(business.displayedWhat())
      .address(business.displayedWhere())
      .openingDaysList(openingDaysList)
      .closedOnHolidays(business.openingHours().closedOnHolidays())
      .openByArrangement(business.openingHours().openByArrangement())
      .build();
  }

  private BusinessResponse getBusiness(String id) {
    final BusinessResponse business;

    try {
      business = businessSearchClient.get(id);
    } catch (FeignClientException ex) {
      switch (ex.getError().getCode()) {
        case FeignClientErrorCode.NO_SUCH_KEY -> throw new BusinessNotFoundException(ex);
        default -> throw new RuntimeException("Unexpected error while searching for business.", ex);
      }
    }

    if (business == null) {
      throw new BusinessNotFoundException();
    }

    return business;
  }

  private List<WebAppBusinessOpeningDays> getOpeningDaysList(final BusinessResponse business) {
    return Arrays.stream(BusinessDay.values())
      // create a mapped version of each day
      .map(day -> getOpeningDaysForDay(day, getDayIntervals(day, business)))
      // sort week days so that they will appear in order
      .sorted((day1, day2) -> Integer.compare(getDayIndex(day1), getDayIndex(day2)))
      // combine days that have the same intervals, as long as they are consecutive
      .reduce(
        (List<WebAppBusinessOpeningDays>) new ArrayList<WebAppBusinessOpeningDays>(),
        (list, openingDays) -> addOpeningDaysToList(openingDays, list),
        (lst1, lst2) -> Stream.concat(lst1.stream(), lst2.stream()).collect(Collectors.toList())
      );
  }

  private int getDayIndex(WebAppBusinessOpeningDays day) {
    return day.days().stream().findFirst()
      .flatMap(BusinessDay::findByCodeMaybe)
      .map(BusinessDay::getIndex)
      .orElseThrow(() -> new RuntimeException("Cannot obtain index of business day."));
  }

  private Set<BusinessOpeningInterval> getDayIntervals(BusinessDay day, BusinessResponse business) {
    var days = business.openingHours().days();

    if (days == null) {
      return Collections.emptySet();
    }

    return business.openingHours().days().getOrDefault(day.getCode(), Collections.emptySet());
  }

  private List<WebAppBusinessOpeningDays> addOpeningDaysToList(WebAppBusinessOpeningDays openingDays, List<WebAppBusinessOpeningDays> list) {
    if (list.size() == 0) {
      list.add(openingDays);
      return list;
    }

    final var last = list.get(list.size() - 1);

    if (last.intervals().size() == openingDays.intervals().size()
      && last.intervals().containsAll(openingDays.intervals())) {
      final WebAppBusinessOpeningDays mergedOpeningDays = WebAppBusinessOpeningDays.builder()
        .days(Stream.concat(last.days().stream(), openingDays.days().stream()).collect(Collectors.toSet()))
        .intervals(last.intervals())
        .build();

      list.set(list.size() - 1, mergedOpeningDays);

      return list;
    }

    list.add(openingDays);

    return list;
  }

  private WebAppBusinessOpeningDays getOpeningDaysForDay(BusinessDay day, Set<BusinessOpeningInterval> intervals) {
    final Set<WebAppBusinessOpeningInterval> webAppIntervals;

    if (intervals != null && !intervals.isEmpty()) {
      webAppIntervals = intervals.stream()
        .map(interval -> WebAppBusinessOpeningInterval.builder()
          .start(interval.start())
          .end(interval.end())
          .type(interval.type())
          .build())
        .collect(Collectors.toSet());
    } else {
      webAppIntervals = Set.of(WebAppBusinessOpeningInterval.builder()
        .type(BusinessOpeningIntervalType.CLOSED)
        .build());
    }

    return WebAppBusinessOpeningDays.builder()
      .days(new HashSet<>(Set.of(day.getCode())))
      .intervals(webAppIntervals)
      .build();
  }
}
