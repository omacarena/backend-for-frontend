package io.mysearch.businesssearch.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalTime;

@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebAppBusinessOpeningInterval(

  @JsonProperty("start")
  LocalTime start,

  @JsonProperty("end")
  LocalTime end,

  @JsonProperty("type")
  String type
) {
}
