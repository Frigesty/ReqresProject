package ru.frigesty.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.models.ErrorResponseModel;
import ru.frigesty.models.RegisterRequestModel;
import ru.frigesty.models.RegisterResponseModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.frigesty.specs.ApiSpecs.*;

@Tag("Registration")
@Epic("Регистрация и авторизация")
@Story("Регистрация пользователя")
@Feature("Регистрация пользователя")
@DisplayName("Тесты на регистрацию пользователя")
public class RegistrationTests extends TestBase {

    @DisplayName("Тест на успешную регистрацию пользователя")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void successfulUserRegistrationTest() {
        RegisterRequestModel requestBody = new RegisterRequestModel();
        requestBody.setEmail("eve.holt@reqres.in");
        requestBody.setPassword("pistol");

        RegisterResponseModel response = step("Отправляем POST-запрос на /api/register", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpecBase)
                        .body("id", equalTo(4))
                        .body("token", notNullValue())
                        .extract().as(RegisterResponseModel.class)
        );

        step("Проверяем значения id и token", () -> {
            assertThat(response.getId())
                    .as("Id не совпадает с ожидаемым значением")
                    .isEqualTo(4);

            assertThat(response.getToken())
                    .as("Токен не должен быть null")
                    .isNotNull();
        });
    }

    @DisplayName("Тест на провальную регистрацию без пароля")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void registrationWithoutPasswordTest() {
        RegisterRequestModel requestBody = new RegisterRequestModel();
        requestBody.setEmail("eve.holt@reqres.in");

        ErrorResponseModel response = step("Отправляем POST-запрос на /api/register без пароля", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(ErrorResponseSpec)
                        .extract().as(ErrorResponseModel.class)
        );

        step("Проверяем сообщение об ошибке", () -> {
            assertThat(response.getError())
                    .as("Ошибка не соответствует ожидаемой")
                    .isEqualTo("Missing password");
        });
    }
}