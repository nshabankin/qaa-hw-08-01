package ru.netology.docker.sql.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.docker.sql.domain.pages.DashboardPage;
import ru.netology.docker.sql.domain.pages.LoginPage;
import ru.netology.docker.sql.domain.pages.VerificationPage;
import ru.netology.docker.sql.data.DemoDataHelper;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static ru.netology.docker.sql.data.DemoDataHelper.clearDatabase;

public class AppLoginTest {

    // Valid hardcoded AuthInfo from demo data
    String validLogin = DemoDataHelper.getValidAuthInfo().getValidLogin();
    String validPassword = DemoDataHelper.getValidAuthInfo().getValidPassword();

    // Invalid hardcoded password
    String invalidPassword = DemoDataHelper.getValidAuthInfo().getInvalidPassword();

    // Get actual AuthInfo from demo data using hardcoded login
    DemoDataHelper.AuthInfo user = DemoDataHelper.getAuthInfoFromDb(validLogin);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    // Method to clear and close the database after all tests
    @AfterAll
    public static void tearDown() {
        clearDatabase();
    }

    @Test
    @SneakyThrows
    @DisplayName("Should login and verify dashboard text")
    void shouldLoginAndVerify() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.login(validLogin, validPassword);

        // Use the method from VerificationPage to check visibility of the code input
        verificationPage.verifyCodeInputIsVisible();

        // Fetch the auth code from the database
        DemoDataHelper.AuthCode authCode = DemoDataHelper.getAuthCodeFromDb(user.getId());
        assertNotNull(authCode, "Auth code should not be null");
        String verificationCode = authCode.getCode();

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.verifyDashboardText("Личный кабинет");
    }

    @Test
    @SneakyThrows
    @DisplayName("Should block user after 3 unsuccessful login attempts")
    void shouldBlockUserAfterThreeFailedAttempts() {
        LoginPage loginPage = new LoginPage();

        // Perform the first invalid login attempt and clear the fields
        loginPage.login(validLogin, invalidPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
        loginPage.clearFields();

        // Perform the second invalid login attempt and clear the fields
        loginPage.login(validLogin, invalidPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
        loginPage.clearFields();

        // Perform the third invalid login attempt and clear the fields
        loginPage.login(validLogin, invalidPassword);
        loginPage.verifyErrorNotification("Ошибка! Неверно указан логин или пароль");
        loginPage.clearFields();

        // Check if the user status is now "blocked"
        assertEquals("blocked", user.getStatus(), "User should be blocked after 3 unsuccessful login attempts");
    }
}
