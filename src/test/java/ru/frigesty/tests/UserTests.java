package ru.frigesty.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.Tag;
import ru.frigesty.models.CreateUserModel;
import ru.frigesty.models.ListUsersModel;
import ru.frigesty.models.SingleUserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.frigesty.models.UpdateUserModel;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static ru.frigesty.specs.ApiSpecs.*;

@Epic("Взаимодействие с пользователем")
@Story("Просмотр, редактирование, удаление пользователя")
@Feature("CRUD пользователя")
@DisplayName("Тесты на CRUD пользователя")
@Tag("User")
public class UserTests extends TestBase {

    @DisplayName("Тест на проверку корректности данных пользователей на 2-й странице")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Frigesty")
    @Test
    void correctUserDataOnSecondPageTest() {
        List<ListUsersModel.UserData> expectedUsers = List.of(
                new ListUsersModel.UserData(7, "michael.lawson@reqres.in", "Michael",
                        "Lawson", "https://reqres.in/img/faces/7-image.jpg"),
                new ListUsersModel.UserData(8, "lindsay.ferguson@reqres.in", "Lindsay",
                        "Ferguson", "https://reqres.in/img/faces/8-image.jpg"),
                new ListUsersModel.UserData(9, "tobias.funke@reqres.in", "Tobias",
                        "Funke", "https://reqres.in/img/faces/9-image.jpg"),
                new ListUsersModel.UserData(10, "byron.fields@reqres.in", "Byron",
                        "Fields", "https://reqres.in/img/faces/10-image.jpg"),
                new ListUsersModel.UserData(11, "george.edwards@reqres.in", "George",
                        "Edwards", "https://reqres.in/img/faces/11-image.jpg"),
                new ListUsersModel.UserData(12, "rachel.howell@reqres.in", "Rachel",
                        "Howell", "https://reqres.in/img/faces/12-image.jpg")
        );

        ListUsersModel response = step("Отправляем запрос на /users?page=2", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/users?page=2")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemes/listUsersScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(ListUsersModel.class)
        );

        List<ListUsersModel.UserData> usersFromResponse = response.getData();

        step("Проверяем, что данные пользователей соответствуют ожидаемым", () -> {
            // Проверяем размер списка
            assertThat(usersFromResponse.size())
                    .as("Некорректное количество пользователей")
                    .isEqualTo(expectedUsers.size());

            // Проверяем данные пользователей
            for (int i = 0; i < expectedUsers.size(); i++) {
                ListUsersModel.UserData expectedUser = expectedUsers.get(i);
                ListUsersModel.UserData actualUser = usersFromResponse.get(i);

                assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
                assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
                assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
                assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
                assertThat(actualUser.getAvatar()).isEqualTo(expectedUser.getAvatar());
            }
        });
    }

    @DisplayName("Тест на получение пользователя по ID")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Frigesty")
    @Test
    public void getUserTest() {
        SingleUserModel response = step("Делаем запрос на получение пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpecBase)
                        .body(matchesJsonSchemaInClasspath("schemes/singleUserScheme.json"))
                        .extract().as(SingleUserModel.class));

        step("Проверяем данные пользователя", () -> {
            assertThat(response.getData().getId()).isEqualTo(2);
            assertThat(response.getData().getEmail()).isEqualTo("janet.weaver@reqres.in");
            assertThat(response.getData().getAvatar()).isEqualTo("https://reqres.in/img/faces/2-image.jpg");
            assertThat(response.getData().getFirstName()).isEqualTo("Janet");
            assertThat(response.getData().getLastName()).isEqualTo("Weaver");
        });
    }

    @DisplayName("Тест на проверку что пользователь не найден")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Frigesty")
    @Test
    public void userNotFoundTest() {
        step("Делаем запрос на несуществующего пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/users/900")
                        .then()
                        .spec(notFoundSpec));
    }

    @DisplayName("Тест на создание нового пользователя")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Frigesty")
    @Test
    public void createUserTest() {
        CreateUserModel requestBody = new CreateUserModel();
        requestBody.setName("morpheus");
        requestBody.setJob("leader");

        step("Отправляем запрос на создание пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .post("/users")
                        .then()
                        .spec(createResponseSpec)
                        .body(matchesJsonSchemaInClasspath("schemes/createUserScheme.json"))
                        .body("name", equalTo(requestBody.getName()))
                        .body("job", equalTo(requestBody.getJob()))
                        .body("id", notNullValue())
                        .body("createdAt", notNullValue()));
    }

    @DisplayName("Тест на обновление данных пользователя")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Frigesty")
    @Test
    public void updateUserTest() {
        UpdateUserModel requestBody = new UpdateUserModel();
        requestBody.setName("morpheus");
        requestBody.setJob("zion resident");

        step("Отправляем запрос на обновление пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .body(requestBody)
                        .when()
                        .put("/users/2")
                        .then()
                        .spec(responseSpecBase)
                        .body(matchesJsonSchemaInClasspath("schemes/updateUserScheme.json"))
                        .body("name", equalTo(requestBody.getName()))
                        .body("job", equalTo(requestBody.getJob()))
                        .body("updatedAt", notNullValue()));
    }

    @DisplayName("Тест на удаление пользователя")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Frigesty")
    @Test
    public void deleteUserTest() {
        step("Отправляем запрос на удаление пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .delete("/users/2")
                        .then()
                        .spec(deleteResponseSpec));
    }

    @DisplayName("Тест на проверку корректности данных пользователей на первой странице с задержкой")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Frigesty")
    @Test
    void correctUserDataOnFirstPageWithDelayTest() {
        List<ListUsersModel.UserData> expectedUsers = List.of(
                new ListUsersModel.UserData(1, "george.bluth@reqres.in", "George",
                        "Bluth", "https://reqres.in/img/faces/1-image.jpg"),
                new ListUsersModel.UserData(2, "janet.weaver@reqres.in", "Janet",
                        "Weaver", "https://reqres.in/img/faces/2-image.jpg"),
                new ListUsersModel.UserData(3, "emma.wong@reqres.in", "Emma",
                        "Wong", "https://reqres.in/img/faces/3-image.jpg"),
                new ListUsersModel.UserData(4, "eve.holt@reqres.in", "Eve",
                        "Holt", "https://reqres.in/img/faces/4-image.jpg"),
                new ListUsersModel.UserData(5, "charles.morris@reqres.in", "Charles",
                        "Morris", "https://reqres.in/img/faces/5-image.jpg"),
                new ListUsersModel.UserData(6, "tracey.ramos@reqres.in", "Tracey",
                        "Ramos", "https://reqres.in/img/faces/6-image.jpg")
        );

        ListUsersModel response = step("Отправляем запрос на /users?page=1 с задержкой 3 секунды", () ->
                given()
                        .spec(requestSpecBase)
                        .queryParam("delay", 3)
                        .when()
                        .get("/users?page=1")
                        .then()
                        .spec(responseSpecBase)
                        .body(matchesJsonSchemaInClasspath("schemes/listUsersScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(ListUsersModel.class)
        );

        List<ListUsersModel.UserData> usersFromResponse = response.getData();

        step("Проверяем, что данные пользователей на 1-й странице соответствуют ожидаемым", () -> {
            assertThat(usersFromResponse.size())
                    .as("Некорректное количество пользователей на первой странице")
                    .isEqualTo(expectedUsers.size());

            for (int i = 0; i < expectedUsers.size(); i++) {
                ListUsersModel.UserData expectedUser = expectedUsers.get(i);
                ListUsersModel.UserData actualUser = usersFromResponse.get(i);

                assertThat(actualUser.getId()).isEqualTo(expectedUser.getId());
                assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
                assertThat(actualUser.getFirstName()).isEqualTo(expectedUser.getFirstName());
                assertThat(actualUser.getLastName()).isEqualTo(expectedUser.getLastName());
                assertThat(actualUser.getAvatar()).isEqualTo(expectedUser.getAvatar());
            }
        });
    }
}