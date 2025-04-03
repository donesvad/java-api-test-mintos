package com.donesvad.assertion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.donesvad.rest.dto.ProblemDetails;
import com.donesvad.rest.dto.User;
import com.donesvad.rest.enums.ProblemDetailsMessageEnum;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UserAssertion {

  public void assertUser(User expectedUser, User actualUser) {
    assertThat("Id mismatch", actualUser.getId(), equalTo(expectedUser.getId()));
    assertThat(
        "First name mismatch", actualUser.getFirstName(), equalTo(expectedUser.getFirstName()));
    assertThat("Last name mismatch", actualUser.getLastName(), equalTo(expectedUser.getLastName()));
    assertThat("Email mismatch", actualUser.getEmail(), equalTo(expectedUser.getEmail()));
    assertThat(
        "Date of birth mismatch",
        actualUser.getDateOfBirth(),
        equalTo(expectedUser.getDateOfBirth()));
    assertThat(
        "Personal id document mismatch",
        actualUser.getPersonalIdDocument(),
        equalTo(expectedUser.getPersonalIdDocument()));
  }

  public void assertUserNotFound(Response getUserResponse) {
    assertThat(
        "There is unexpected user",
        getUserResponse.getStatusCode(),
        equalTo(HttpStatus.SC_NOT_FOUND));
  }

  public void assertErrorMessage(
      ProblemDetails problemDetails, ProblemDetailsMessageEnum problemDetailsMessageEnum) {
    assertThat(
        "Error message mismatch",
        problemDetails.getDetail(),
        equalTo(problemDetailsMessageEnum.getDetail()));
  }

  public void assertUnauthorized(Response getUserResponse) {
    assertThat(
        "Unauthorized access",
        getUserResponse.getStatusCode(),
        equalTo(HttpStatus.SC_UNAUTHORIZED));
  }
}
