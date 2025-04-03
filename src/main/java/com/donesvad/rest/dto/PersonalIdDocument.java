package com.donesvad.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude
@Data
public class PersonalIdDocument {

  @JsonProperty("documentId")
  private String documentId;

  @JsonProperty("countryOfIssue")
  private String countryOfIssue;

  @JsonProperty("validUntil")
  private String validUntil;
}
