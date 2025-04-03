package com.donesvad.scenario;

import com.donesvad.rest.dto.ProblemDetails;
import com.donesvad.rest.dto.User;
import com.donesvad.rest.enums.ProblemDetailsMessageEnum;
import com.github.javafaker.Faker;
import java.util.List;
import java.util.Random;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

@CommonsLog
@TestInstance(Lifecycle.PER_CLASS)
public class UserTest extends BaseTest {

  @ParameterizedTest
  @MethodSource("getUserTestData")
  void createAndGetByIdUserTest(User user) {
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
    userAction.postUser(user);
    userAssertion.assertUser(user, userAction.getUserById(user.getId()));
  }

  @Test
  void updateUserIdTest() {
    User user = getRandomUser();
    userAction.postUser(user);
    Faker faker = new Faker();
    String newEmail = faker.internet().emailAddress();
    user.setEmail(newEmail);
    userAction.updateUser(user.getId(), user);
    userAssertion.assertUser(user, userAction.getUserById(user.getId()));
  }

  @Test
  void deleteUserTest() {
    User user = getRandomUser();
    userAction.postUser(user);
    userAction.deleteUser(user.getId());
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
  }

  @Test
  void createInvalidUserIdTest() {
    User user = getRandomUser();
    user.setId(null);
    ProblemDetails problemDetails = userAction.postInvalidUser(user);
    userAssertion.assertErrorMessage(problemDetails, ProblemDetailsMessageEnum.ID);
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
  }

  @Test
  void createInvalidUserLastNameTest() {
    User user = getRandomUser();
    user.setLastName("a");
    ProblemDetails problemDetails = userAction.postInvalidUser(user);
    userAssertion.assertErrorMessage(problemDetails, ProblemDetailsMessageEnum.LAST_NAME);
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
  }

  @Test
  void updateInvalidUserCoITest() {
    User user = getRandomUser();
    userAction.postUser(user);
    String initialCountryOfIssue = user.getPersonalIdDocument().getCountryOfIssue();
    String wrongCountryOfIssue = "MDDDD";
    user.getPersonalIdDocument().setCountryOfIssue(wrongCountryOfIssue);
    ProblemDetails problemDetails = userAction.updateInvalidUser(user.getId(), user);
    userAssertion.assertErrorMessage(problemDetails, ProblemDetailsMessageEnum.COUNTRY_OF_ISSUE);
    user.getPersonalIdDocument().setCountryOfIssue(initialCountryOfIssue);
    userAssertion.assertUser(user, userAction.getUserById(user.getId()));
  }

  @Test
  void deleteInvalidUserIdTest() {
    User user = getRandomUser();
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
    userAction.deleteInvalidUser(user.getId());
    userAssertion.assertUserNotFound(userAction.getUserByIdNotFound(user.getId()));
  }

  private User getRandomUser() {
    List<User> users = getUserTestData();
    if (users == null || users.isEmpty()) {
      throw new RuntimeException("User test data is empty");
    }

    Random random = new Random();
    int randomIndex = random.nextInt(users.size());
    return users.get(randomIndex);
  }
}
