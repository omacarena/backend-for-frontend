package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Map;
import java.util.Set;

@Builder(toBuilder = true)
public record BusinessOpeningHours(

  @JsonProperty("days")
  Map<String, Set<BusinessOpeningInterval>> days,

  @JsonProperty("closed_on_holidays")
  Boolean closedOnHolidays,

  @JsonProperty("open_by_arrangement")
  Boolean openByArrangement
) {
}
