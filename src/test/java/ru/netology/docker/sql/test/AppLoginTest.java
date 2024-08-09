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

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.docker.sql.data.DemoDataHelper.clearDatabase;
import static ru.netology.docker.sql.data.DemoDataHelper.getConnection;

public class AppLoginTest {

    // Valid hardcoded AuthInfo from demo data
    String validLogin = DemoDataHelper.getValidAuthInfo().getValidLogin();
    String validPassword = DemoDataHelper.getValidAuthInfo().getValidPassword();

    // Get actual AuthInfo from demo data using hardcoded login
    DemoDataHelper.AuthInfo user = DemoDataHelper.getAuthInfoFromDb(validLogin);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    // Method to clear and close the database after all tests
    @AfterAll
    @SneakyThrows
    public static void tearDown() {
        clearDatabase();
        if (getConnection() != null && !getConnection().isClosed()) {
            getConnection().close(); // Close the database connection
        }
    }



    @Test
    @SneakyThrows
    @DisplayName("Should login and verify dashboard text")
    void shouldLoginAndVerify() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);

        // Wait until the verification page is visible
        $("[data-test-id='code'] input").shouldBe(visible);

        // Fetch the auth code from the database
        DemoDataHelper.AuthCode authCode = DemoDataHelper.getAuthCodeFromDb(user.getId());
        assert authCode != null;
        String verificationCode = authCode.getCode();

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.verifyDashboardText("Личный кабинет");
    }
}
