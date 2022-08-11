package tests;

import models.lombok.CreateUserRequestModel;
import models.lombok.CreateUserResponseModel;
import models.lombok.UserLoginRequestModel;
import models.lombok.UserLoginResponseModel;
import models.pojo.UserDataRequestModel;
import models.pojo.UserDataResponseModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.CreateUserSpec.createRequestSpec;
import static specs.CreateUserSpec.createResponseSpec;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;
import static specs.PatchSpec.patchRequestSpec;
import static specs.PatchSpec.patchResponseSpec;

public class ReqresInTests extends TestBase {

    @Test
    @DisplayName("Login with lombok and spec")
    public void LoginTest() {
        UserLoginRequestModel loginBody = new UserLoginRequestModel();
        loginBody.setEmail("eve.holt@reqres.in");
        loginBody.setPassword("cityslicka");

        UserLoginResponseModel loginResponse = given()
                .spec(loginRequestSpec)
                .body(loginBody)
                .when()
                .post()
                .then()
                .spec(loginResponseSpec)
                .extract().as(UserLoginResponseModel.class);

        assertThat(loginResponse.getToken()).isEqualTo("QpwL5tke4Pnpja7X4");
    }

    @Test
    @DisplayName("Create user with lombok and spec")
    public void createUserTest() {
        CreateUserRequestModel user = new CreateUserRequestModel();
        user.setName("morpheus");
        user.setJob("leader");
        CreateUserResponseModel userResponse = given()
                .spec(createRequestSpec)
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(createResponseSpec)
                .extract().as(CreateUserResponseModel.class);
        assertThat(userResponse.getJob()).isEqualTo("leader");
        assertThat(userResponse.getName()).isEqualTo("morpheus");
    }

    @Test
    @DisplayName("Patch with pojo and spec")
    public void patchUser() {
        UserDataRequestModel userData = new UserDataRequestModel();
        userData.setName("morpheus");
        userData.setJob("zion resident");

        UserDataResponseModel responseModel = given()
                .spec(patchRequestSpec)
                .body(userData)
                .when()
                .patch()
                .then()
                .spec(patchResponseSpec)
                .extract().as(UserDataResponseModel.class);

        assertThat(responseModel.getJob()).isEqualTo("zion resident");
        assertThat(responseModel.getName()).isEqualTo("morpheus");
    }

}
