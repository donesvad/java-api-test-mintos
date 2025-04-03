package com.donesvad.action;

import com.donesvad.rest.dto.ProblemDetails;
import com.donesvad.rest.dto.User;
import com.donesvad.rest.service.UserService;
import io.restassured.response.Response;
import java.util.List;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAction {

  @Autowired protected UserService userService;

  public List<User> getAllUsers() {
    return userService.getAllUsers(HttpStatus.SC_OK).body().jsonPath().getList("", User.class);
  }

  public Response getUserByIdNotFound(String userId) {
    return userService.getUserById(userId, HttpStatus.SC_NOT_FOUND);
  }

  public User getUserById(String userId) {
    return userService.getUserById(userId, HttpStatus.SC_OK).body().as(User.class);
  }

  public void postUser(User user) {
    userService.createUser(user, HttpStatus.SC_CREATED);
  }

  public ProblemDetails postInvalidUser(User user) {
    return userService.createUser(user, HttpStatus.SC_BAD_REQUEST).as(ProblemDetails.class);
  }

  public void updateUser(String userId, User user) {
    userService.updateUser(userId, user, HttpStatus.SC_OK);
  }

  public ProblemDetails updateInvalidUser(String userId, User user) {
    return userService.updateUser(userId, user, HttpStatus.SC_BAD_REQUEST).as(ProblemDetails.class);
  }

  public void deleteUser(String userId) {
    userService.deleteUser(userId, HttpStatus.SC_NO_CONTENT);
  }

  public void deleteInvalidUser(String userId) {
    userService.deleteUser(userId, HttpStatus.SC_NOT_FOUND);
  }

  public void deleteAnyUser(String userId) {
    userService.deleteUser(userId, null);
  }

  public Response getAllUsersWithoutAuth() {
    return userService.getAllUsersWithoutAuth(HttpStatus.SC_UNAUTHORIZED);
  }
}
