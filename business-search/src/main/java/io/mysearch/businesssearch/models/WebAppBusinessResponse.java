package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record WebAppBusinessResponse(

  @JsonProperty("name")
  String name,

  @JsonProperty("address")
  String address,

  @JsonProperty("opening_days")
  List<WebAppBusinessOpeningDays> openingDaysList,

  @JsonProperty("closed_on_holidays")
  Boolean closedOnHolidays,

  @JsonProperty("open_by_arrangement")
  Boolean openByArrangement
) {
}
