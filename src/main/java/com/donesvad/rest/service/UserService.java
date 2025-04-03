package com.donesvad.rest.service;

import static io.restassured.RestAssured.config;
import static io.restassured.RestAssured.given;

import com.donesvad.rest.dto.User;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ParamConfig;
import io.restassured.config.ParamConfig.UpdateStrategy;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  public static final String ID = "id";
  public static final String PATH_PARAM_ID = "/{" + ID + "}";

  private RequestSpecification requestSpec;

  @Value("${user-service-rest.host}")
  private String host;

  @Value("${user-service-rest.port:#{null}}")
  private Integer port;

  @Value("${user-service-rest.protocol}")
  private String protocol;

  @Value("${user-service-rest.base-path}")
  private String basePath;

  @Value("${user-service-rest.username}")
  private String username;

  @Value("${user-service-rest.password}")
  private String password;

  @PostConstruct
  public void init() {
    String baseUri = protocol + "://" + host;
    if (port != null) {
      baseUri += ":" + port;
    }
    requestSpec =
        new RequestSpecBuilder()
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.BODY)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setBaseUri(baseUri)
            .setBasePath(basePath)
            .setConfig(
                config()
                    .paramConfig(
                        ParamConfig.paramConfig()
                            .queryParamsUpdateStrategy(UpdateStrategy.REPLACE)))
            //            .addHeader(
            //                "Authorization",
            //                given().auth().basic(username,
            // password).get("").header("Authorization"))
            .build();
  }

  public Response getAllUsers(int expectedStatusCode) {
    return given(requestSpec).get().then().statusCode(expectedStatusCode).extract().response();
  }

  public Response getUserById(String userId, int expectedStatusCode) {
    return given(requestSpec)
        .pathParam(ID, userId)
        .get(PATH_PARAM_ID)
        .then()
        .statusCode(expectedStatusCode)
        .extract()
        .response();
  }

  public Response createUser(User user, int expectedStatusCode) {
    return given(requestSpec)
        .body(user)
        .post()
        .then()
        .statusCode(expectedStatusCode)
        .extract()
        .response();
  }

  public Response updateUser(String userId, User user, Integer expectedStatusCode) {
    return given(requestSpec)
        .pathParam(ID, userId)
        .body(user)
        .put(PATH_PARAM_ID)
        .then()
        .statusCode(expectedStatusCode)
        .extract()
        .response();
  }

  public Response deleteUser(String userId, Integer expectedStatusCode) {
    ValidatableResponse validatableResponse =
        given(requestSpec).pathParam(ID, userId).delete(PATH_PARAM_ID).then();
    if (expectedStatusCode != null) {
      validatableResponse = validatableResponse.statusCode(expectedStatusCode);
    }
    return validatableResponse.extract().response();
  }

  public Response getAllUsersWithoutAuth(int expectedStatusCode) {
    String baseUri = protocol + "://" + host;
    if (port != null) {
      baseUri += ":" + port;
    }
    RequestSpecification unauthenticatedRequestSpec =
        new RequestSpecBuilder()
            .log(LogDetail.URI)
            .log(LogDetail.METHOD)
            .log(LogDetail.BODY)
            .setContentType(ContentType.JSON)
            .setAccept(ContentType.JSON)
            .setBaseUri(baseUri)
            .setBasePath(basePath)
            .setConfig(
                config()
                    .paramConfig(
                        ParamConfig.paramConfig()
                            .queryParamsUpdateStrategy(UpdateStrategy.REPLACE)))
            .build();

    return given(unauthenticatedRequestSpec)
        .get()
        .then()
        .statusCode(expectedStatusCode)
        .extract()
        .response();
  }
}
