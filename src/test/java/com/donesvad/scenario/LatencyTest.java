package com.donesvad.scenario;

import io.restassured.response.Response;
import lombok.extern.apachecommons.CommonsLog;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@CommonsLog
@Tag("performance")
public class LatencyTest extends BaseTest {

  @Test
  void testGetAllUsersWithoutAuth() {
    Response response = userAction.getAllUsersWithoutAuth();
    userAssertion.assertUnauthorized(response);
  }
}
