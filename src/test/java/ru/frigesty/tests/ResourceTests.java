package ru.frigesty.tests;

import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ru.frigesty.models.ListResourceModel;
import ru.frigesty.models.SingleResourceModel;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.frigesty.specs.ApiSpecs.*;

@Tag("Resource")
@Epic("Работа с ресурсами")
@Feature("Получение информации о цветах")
@Story("Получение списка и отдельного ресурса по ID")
@DisplayName("Тесты на работу с цветами")
public class ResourceTests extends TestBase {

    @DisplayName("Тест на проверка корректности данных в List Resource")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    void correctDataInPageListResourceTest() {
        List<ListResourceModel.ColorData> expectedColors = List.of(
                new ListResourceModel.ColorData(1, 2000, "cerulean", "#98B2D1",
                        "15-4020"),
                new ListResourceModel.ColorData(2, 2001, "fuchsia rose", "#C74375",
                        "17-2031"),
                new ListResourceModel.ColorData(3, 2002, "true red", "#BF1932",
                        "19-1664"),
                new ListResourceModel.ColorData(4, 2003, "aqua sky", "#7BC4C4",
                        "14-4811"),
                new ListResourceModel.ColorData(5, 2004, "tigerlily", "#E2583E",
                        "17-1456"),
                new ListResourceModel.ColorData(6, 2005, "blue turquoise", "#53B0AE",
                        "15-5217")
        );

        ListResourceModel response = step("Делаем запрос", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/unknown")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemes/listResourceScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(ListResourceModel.class));

        List<ListResourceModel.ColorData> colorsFromResponse = response.getData();

        step("Проверяем все цвета в массиве data", () -> {
            assertEquals(expectedColors.size(), colorsFromResponse.size(),
                    "Некорректное количество элементов в data");
            for (int i = 0; i < expectedColors.size(); i++) {
                assertEquals(expectedColors.get(i).getId(), colorsFromResponse.get(i).getId());
                assertEquals(expectedColors.get(i).getYear(), colorsFromResponse.get(i).getYear());
                assertEquals(expectedColors.get(i).getName(), colorsFromResponse.get(i).getName());
                assertEquals(expectedColors.get(i).getColor(), colorsFromResponse.get(i).getColor());
                assertEquals(expectedColors.get(i).getPantoneValue(), colorsFromResponse.get(i).getPantoneValue());
            }
        });
    }

    @DisplayName("Тест на получение цвета по ID")
    @Owner("Frigesty")
    @Severity(SeverityLevel.CRITICAL)
    @Test
    public void getResourceByIdTest() {
        SingleResourceModel response = step("Делаем запрос", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/unknown/2")
                        .then()
                        .body(matchesJsonSchemaInClasspath("schemes/singleResourceScheme.json"))
                        .spec(responseSpecBase)
                        .extract().as(SingleResourceModel.class));

        step("Проверяем данные ресурса", () -> {
            assertEquals(2, response.getData().getId());
            assertEquals("fuchsia rose", response.getData().getName());
            assertEquals(2001, response.getData().getYear());
            assertEquals("#C74375", response.getData().getColor());
            assertEquals("17-2031", response.getData().getPantoneValue());
        });
    }

    @DisplayName("Тест на то что цвет не найден")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Frigesty")
    @Test
    public void userNotFoundTest() {
        step("Делаем запрос на несуществующего пользователя", () ->
                given()
                        .spec(requestSpecBase)
                        .when()
                        .get("/api/unknown/23")
                        .then()
                        .spec(notFoundSpec));
    }
}