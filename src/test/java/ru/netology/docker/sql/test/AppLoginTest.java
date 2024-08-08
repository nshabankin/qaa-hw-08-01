package ru.netology.docker.sql.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.docker.sql.domain.pages.DashboardPage;
import ru.netology.docker.sql.domain.pages.LoginPage;
import ru.netology.docker.sql.domain.pages.VerificationPage;
import ru.netology.docker.sql.data.DemoDataHelper;

import static com.codeborne.selenide.Selenide.*;

public class AppLoginTest {

    String validLogin = "petya";
    String validPassword = "123qwerty";
    DemoDataHelper.AuthInfo user = DemoDataHelper.getAuthInfoFromDb(validLogin);

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    @SneakyThrows
    @DisplayName("Should login and verify dashboard text")
    void shouldLoginAndVerify() {
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin(validLogin, validPassword);
        Thread.sleep(1000);

        // Fetch the auth code from the database
        DemoDataHelper.AuthCode authCode = DemoDataHelper.getAuthCodeFromDb(user.getUserId());
        String verificationCode = authCode.getCode();

        DashboardPage dashboardPage = verificationPage.validVerify(verificationCode);

        dashboardPage.verifyDashboardText("Личный кабинет");
    }
}
