package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder(toBuilder = true)
public record BusinessResponse(

  @JsonProperty("displayed_what")
  String displayedWhat,

  @JsonProperty("displayed_where")
  String displayedWhere,

  @JsonProperty("opening_hours")
  BusinessOpeningHours openingHours
) {
}
