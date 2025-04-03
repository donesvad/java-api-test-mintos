package com.donesvad.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude
@Data
public class ProblemDetails {

  @JsonProperty("type")
  private final String type;

  @JsonProperty("title")
  private final String title;

  @JsonProperty("status")
  private final Integer status;

  @JsonProperty("detail")
  private final String detail;

  @JsonProperty("instance")
  private final String instance;
}
