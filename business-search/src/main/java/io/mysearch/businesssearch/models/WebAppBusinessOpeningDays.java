package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Collections;
import java.util.Set;

@Builder(toBuilder = true)
public record WebAppBusinessOpeningDays(

  @JsonProperty("days")
  Set<String> days,

  @JsonProperty("intervals")
  Set<WebAppBusinessOpeningInterval> intervals
) {

  public static WebAppBusinessOpeningDays empty() {
    return WebAppBusinessOpeningDays.builder()
      .days(Collections.emptySet())
      .intervals(Collections.emptySet())
      .build();
  }
}
