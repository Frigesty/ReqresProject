package ru.frigesty.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.models.ErrorResponseModel;
import ru.frigesty.models.LoginRequestModel;
import ru.frigesty.models.LoginResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.frigesty.specs.ApiSpecs.*;

@Tag("Login")
@Epic("Регистрация и авторизация")
@Story("Авторизация пользователя")
@Feature("Авторизация пользователя")
@DisplayName("Тесты на авторизацию пользователя")
public class LoginTests extends TestBase {

    @DisplayName("Тест на успешный логин пользователя")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void successfulUserLoginTest() {

        LoginRequestModel requestBody = new LoginRequestModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("cityslicka");

        String expectedToken = "QpwL5tke4Pnpja7X4";

        LoginResponseModel response = step("Отправляем POST-запрос на /api/login", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(responseSpecBase)
                        .body("token", notNullValue())
                        .extract().as(LoginResponseModel.class)
        );

        step("Проверяем, что получен ожидаемый токен", () -> {
            assertEquals(expectedToken, response.getToken(), "Токен не совпадает с ожидаемым значением");
        });
    }

    @DisplayName("Тест на провальный логин без пароля")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void loginWithMissingPasswordTest() {

        LoginRequestModel requestBody = new LoginRequestModel();
        requestBody.setEmail("peter@klaven");

        ErrorResponseModel response = step("Отправляем POST-запрос на /api/login без пароля", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .post("/login")
                        .then()
                        .spec(ErrorResponseSpec)
                        .body("error", equalTo("Missing password"))
                        .extract().as(ErrorResponseModel.class)
        );

        step("Проверяем, что ошибка в ответе соответствует ожидаемой", () -> {
            assertEquals("Missing password", response.getError(),
                    "Ошибка в сообщении не совпадает с ожидаемой");
        });
    }
}