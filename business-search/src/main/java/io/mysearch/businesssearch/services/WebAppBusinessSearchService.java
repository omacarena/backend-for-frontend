package io.mysearch.businesssearch.services;

import io.mysearch.businesssearch.clients.BusinessSearchClient;
import io.mysearch.businesssearch.models.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WebAppBusinessSearchService {

  private final BusinessSearchClient businessSearchClient;

  public WebAppBusinessResponse search(String id) {
    final ResponseEntity<BusinessResponse> response = businessSearchClient.get(id);

    final BusinessResponse business = response.getBody();

    if (business == null) {
      return null;
    }

    final List<WebAppBusinessOpeningDays> openingDaysList = getOpeningDaysList(business);

    return WebAppBusinessResponse.builder()
      .name(business.displayedWhat())
      .address(business.displayedWhere())
      .openingDaysList(openingDaysList)
      .build();
  }

  private List<WebAppBusinessOpeningDays> getOpeningDaysList(final BusinessResponse business) {
    return Arrays.stream(BusinessDay.values())
      .map(day -> getOpeningDaysForDay(day, business.openingHours().days().get(day.getCode())))
      .reduce(
        (List<WebAppBusinessOpeningDays>) new ArrayList<WebAppBusinessOpeningDays>(),
        (lst, openingDaysCur) -> {
          if (lst.size() == 0) {
            lst.add(openingDaysCur);
            return lst;
          }

          final WebAppBusinessOpeningDays last = lst.get(lst.size() - 1);

          if (last.intervals().containsAll(openingDaysCur.intervals())) {
            final WebAppBusinessOpeningDays mergedOpeningDays = WebAppBusinessOpeningDays.builder()
              .days(Stream.concat(last.days().stream(), openingDaysCur.days().stream()).collect(Collectors.toSet()))
              .intervals(last.intervals())
              .build();

            lst.set(lst.size() - 1, mergedOpeningDays);

            return lst;
          }

          lst.add(openingDaysCur);

          return lst;
        },
        (lst1, lst2) -> Stream.concat(lst1.stream(), lst2.stream()).collect(Collectors.toList())
      );
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
