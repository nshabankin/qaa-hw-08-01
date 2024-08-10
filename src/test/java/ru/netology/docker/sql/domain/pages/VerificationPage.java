package ru.netology.docker.sql.domain.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {

    // Define the Selenide element for the verification code input field
    private final SelenideElement verificationCodeInput = $("[data-test-id='code'] input");

    // Method to verify the visibility of the verification code input field
    public void verifyCodeInputIsVisible() {
        verificationCodeInput.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verificationCodeInput.setValue(verificationCode);
        $("[data-test-id='action-verify']").click();
        return new DashboardPage();
    }
}
