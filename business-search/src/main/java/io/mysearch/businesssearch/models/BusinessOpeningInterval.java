package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalTime;

@Builder(toBuilder = true)
public record BusinessOpeningInterval(

  @JsonProperty("start")
  LocalTime start,

  @JsonProperty("end")
  LocalTime end,

  @JsonProperty("type")
  String type
) {
}
