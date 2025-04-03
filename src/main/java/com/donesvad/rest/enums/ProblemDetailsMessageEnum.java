package com.donesvad.rest.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProblemDetailsMessageEnum {
  ID("The id must not be a null."),
  LAST_NAME("The last name should be in a valid format."),
  COUNTRY_OF_ISSUE("The countryOfIssue must be an ISO 3166-1 alpha-2 code.");

  private final String detail;
}
