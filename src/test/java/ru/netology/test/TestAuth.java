package ru.netology.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGeneration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestAuth {

    @BeforeEach
    void Setup() {
        open("http://localhost:9999");
    }


    @Test
    void shouldUserActive() {
        val user = DataGeneration.RegistrationData.generateUser("active");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin());
        $("[type='password']").setValue(user.getPassword());
        $(".button__text").click();
        $(withText("Личный кабинет")).shouldBe(visible);
    }

    @Test
    void shouldUserBlocked() {
        val user = DataGeneration.RegistrationData.generateUser("blocked");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin());
        $("[type='password']").setValue(user.getPassword());
        $(".button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(exactText("Ошибка! Пользователь заблокирован")).shouldBe(visible);;
    }

    @Test
    void shouldWrongLoginStatusActive() {
        val user = DataGeneration.RegistrationData.generateUser("active");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin()+"-WRONG");
        $("[type='password']").setValue(user.getPassword());
        $(".button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);;
    }

    @Test
    void shouldWrongLoginStatusBlocked() {
        val user = DataGeneration.RegistrationData.generateUser("blocked");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin()+"-WRONG");
        $("[type='password']").setValue(user.getPassword());
        $(".button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);;
    }

    @Test
    void shouldWrongPasswordStatusActive() {
        val user = DataGeneration.RegistrationData.generateUser("active");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin());
        $("[type='password']").setValue(user.getPassword()+"-WRONG");
        $(".button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);;
    }

    @Test
    void shouldWrongPasswordStatusBlocked() {
        val user = DataGeneration.RegistrationData.generateUser("blocked");
        DataGeneration.setUpAll(user);
        $("[type='text']").setValue(user.getLogin());
        $("[type='password']").setValue(user.getPassword()+"-WRONG");
        $(".button__text").click();
        $("[data-test-id='error-notification'] .notification__content")
                .should(exactText("Ошибка! Неверно указан логин или пароль")).shouldBe(visible);;
    }

}
