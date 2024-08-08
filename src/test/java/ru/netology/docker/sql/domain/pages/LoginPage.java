package ru.netology.docker.sql.domain.pages;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    public VerificationPage validLogin(String username, String password) {
        $("[data-test-id='login'] input").setValue(username).shouldBe(visible);
        $("[data-test-id='password'] input").setValue(password).shouldBe(visible);
        $("[data-test-id='action-login']").click();
        return new VerificationPage();
    }
}
