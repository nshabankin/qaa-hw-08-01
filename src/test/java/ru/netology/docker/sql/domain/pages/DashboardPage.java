package ru.netology.docker.sql.domain.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class DashboardPage {

    // Define a Selenide element representing the dashboard text
    private final SelenideElement dashboardHeading = $("[data-test-id='dashboard']");

    // Method to verify the presence of the dashboard text
    public void verifyDashboardText(String expectedText) {
        dashboardHeading.shouldBe(visible).shouldHave(text(expectedText));
    }
}