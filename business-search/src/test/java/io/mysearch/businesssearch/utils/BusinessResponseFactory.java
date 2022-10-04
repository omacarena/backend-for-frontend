package io.mysearch.businesssearch.utils;

import io.mysearch.businesssearch.models.*;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class BusinessResponseFactory {

  private static final LocalTime TIME_1 = LocalTime.of(1, 2, 3);
  private static final LocalTime TIME_2 = LocalTime.of(2, 3, 4);
  private static final LocalTime TIME_3 = LocalTime.of(3, 4, 5);
  private static final LocalTime TIME_4 = LocalTime.of(4, 5, 6);
  private static final LocalTime TIME_5 = LocalTime.of(5, 6, 7);
  private static final LocalTime TIME_6 = LocalTime.of(6, 7, 8);

  public static BusinessResponse createBusinessResponse(int index, BusinessOpeningHours openingHours) {
    return BusinessResponse.builder()
      .displayedWhat("business " + index)
      .displayedWhere("business address " + index)
      .openingHours(openingHours)
      .build();
  }

  public static WebAppBusinessResponse createWebAppBusinessResponse(int index, List<WebAppBusinessOpeningDays> openingDaysList) {
    return WebAppBusinessResponse.builder()
      .name("business " + index)
      .address("business address " + index)
      .openingDaysList(openingDaysList)
      .closedOnHolidays(true)
      .openByArrangement(true)
      .build();
  }

  public static BusinessOpeningHours noHours() {
    return BusinessOpeningHours.builder()
      .closedOnHolidays(true)
      .openByArrangement(true)
      .build();
  }

  public static BusinessOpeningHours onlyClosedHours() {
    return BusinessOpeningHours.builder()
      .days(Map.of(
        BusinessDay.TUESDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .build()
        ),
        BusinessDay.THURSDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .build()
        )
      ))
      .closedOnHolidays(true)
      .openByArrangement(true)
      .build();
  }

  public static List<WebAppBusinessOpeningDays> webAppOnlyClosedHours() {
    return List.of(WebAppBusinessOpeningDays.builder()
      .days(Arrays.stream(BusinessDay.values())
        .map(BusinessDay::getCode)
        .collect(Collectors.toSet()))
      .intervals(Set.of(WebAppBusinessOpeningInterval.builder()
        .type(BusinessOpeningIntervalType.CLOSED)
        .build()))
      .build());
  }

  public static BusinessOpeningHours normalWeekHours() {
    return BusinessOpeningHours.builder()
      .days(Map.of(
        BusinessDay.TUESDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .start(TIME_1)
            .end(TIME_2)
            .type(BusinessOpeningIntervalType.OPEN)
            .build(),
          BusinessOpeningInterval.builder()
            .start(TIME_3)
            .end(TIME_4)
            .type(BusinessOpeningIntervalType.CLOSED)
            .build(),
          BusinessOpeningInterval.builder()
            .start(TIME_5)
            .end(TIME_6)
            .type(BusinessOpeningIntervalType.OPEN)
            .build()
        ),
        BusinessDay.THURSDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .start(TIME_1)
            .end(TIME_2)
            .type(BusinessOpeningIntervalType.OPEN)
            .build()
        ),
        BusinessDay.WEDNESDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .start(TIME_1)
            .end(TIME_2)
            .type(BusinessOpeningIntervalType.OPEN)
            .build(),
          BusinessOpeningInterval.builder()
            .start(TIME_3)
            .end(TIME_4)
            .type(BusinessOpeningIntervalType.CLOSED)
            .build(),
          BusinessOpeningInterval.builder()
            .start(TIME_5)
            .end(TIME_6)
            .type(BusinessOpeningIntervalType.OPEN)
            .build()
        ),
        BusinessDay.FRIDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .start(TIME_1)
            .end(TIME_2)
            .type(BusinessOpeningIntervalType.OPEN)
            .build()
        ),
        BusinessDay.SUNDAY.getCode(), Set.of(
          BusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .build()
        )
      ))
      .closedOnHolidays(true)
      .openByArrangement(true)
      .build();
  }


  public static List<WebAppBusinessOpeningDays> webAppNormalWeekHours() {
    return List.of(
      WebAppBusinessOpeningDays.builder()
        .days(Set.of(BusinessDay.MONDAY.getCode()))
        .intervals(Set.of(
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .build()
        ))
        .build(),

      WebAppBusinessOpeningDays.builder()
        .days(Set.of(BusinessDay.TUESDAY.getCode(), BusinessDay.WEDNESDAY.getCode()))
        .intervals(Set.of(
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.OPEN)
            .start(TIME_1)
            .end(TIME_2)
            .build(),
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .start(TIME_3)
            .end(TIME_4)
            .build(),
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.OPEN)
            .start(TIME_5)
            .end(TIME_6)
            .build()
        ))
        .build(),

      WebAppBusinessOpeningDays.builder()
        .days(Set.of(BusinessDay.THURSDAY.getCode(), BusinessDay.FRIDAY.getCode()))
        .intervals(Set.of(
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.OPEN)
            .start(TIME_1)
            .end(TIME_2)
            .build()
        ))
        .build(),

      WebAppBusinessOpeningDays.builder()
        .days(Set.of(BusinessDay.SATURDAY.getCode(), BusinessDay.SUNDAY.getCode()))
        .intervals(Set.of(
          WebAppBusinessOpeningInterval.builder()
            .type(BusinessOpeningIntervalType.CLOSED)
            .build()
        ))
        .build()
    );
  }
}
