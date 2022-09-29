package io.mysearch.businesssearch.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public enum BusinessDay {

  MONDAY(0, "monday"),
  TUESDAY(1, "tuesday"),
  WEDNESDAY(2, "wednesday"),
  THURSDAY(3, "thursday"),
  FRIDAY(4, "friday"),
  SATURDAY(5, "saturday"),
  SUNDAY(6, "sunday");

  private final int index;
  private final String code;

  public static Optional<BusinessDay> findByCodeMaybe(String code) {
    return Arrays.stream(values())
      .filter(value -> value.code.equals(code))
      .findFirst();
  }
}
