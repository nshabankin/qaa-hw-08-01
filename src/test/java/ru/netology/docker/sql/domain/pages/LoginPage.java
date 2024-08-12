package ru.netology.docker.sql.domain.pages;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    // Define the Selenide elements for login form
    private final SelenideElement loginField = $("[data-test-id='login'] input");
    private final SelenideElement passwordField = $("[data-test-id='password'] input");
    private final SelenideElement loginButton = $("[data-test-id='action-login']");
    private final SelenideElement errorNotification = $("[data-test-id='error-notification']");

    public VerificationPage login(String username, String password) {
        loginField.shouldBe(visible).setValue(username);
        passwordField.shouldBe(visible).setValue(password);
        loginButton.shouldBe(visible).click();
        return new VerificationPage();
    }

    // Method to clear the password field
    public void clearFields() {
        loginField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
    }

    // Method to verify the appearance of the error notification
    public void verifyErrorNotification(String expectedMessage) {
        errorNotification.shouldBe(visible).shouldHave(text(expectedMessage));
    }
}
