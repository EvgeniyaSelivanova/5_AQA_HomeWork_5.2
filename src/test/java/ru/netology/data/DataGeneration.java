package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

public class DataGeneration {

    private DataGeneration() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void setUpAll(RegistrationData.UserInfo registrationData) {
        RestAssured.given();
        given() // "дано"
                .spec(requestSpec) // указываем, какую спецификацию используем
                .body(registrationData) // передаём в теле объект, который будет преобразован в JSON
                .when() // "когда"
                .post("/api/system/users") // на какой путь, относительно BaseUri отправляем запрос
                .then() // "тогда ожидаем"
                .statusCode(200); // код 200 OK
    }

    public static class RegistrationData {
        private RegistrationData() {
        }

        public static String generateName() {
            return new Faker(new Locale("en")).name().username();

        }

        public static String generatePassword() {
            return new Faker(new Locale("en")).internet().password();
        }

        public static UserInfo generateUser(String status) {
            return new UserInfo(
                    generateName(),
                    generatePassword(),
                    status
            );
        }

        @Value
            public static class UserInfo {
                String login;
                String password;
                String status;
            }

    }
}
