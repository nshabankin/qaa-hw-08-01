package ru.netology.docker.sql.test;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.docker.sql.domain.pages.DashboardPage;
import ru.netology.docker.sql.domain.pages.LoginPage;
import ru.netology.docker.sql.domain.pages.VerificationPage;
import ru.netology.docker.sql.data.DemoDataHelper;
import ru.netology.docker.sql.util.SQLQueries;

import java.sql.Connection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.docker.sql.data.DemoDataHelper.getConnection;

public class AppLoginTest {

    // Valid hardcoded AuthInfo from demo data
    String validLogin = DemoDataHelper.getValidAuthInfo().getValidLogin();
    String validPassword = DemoDataHelper.getValidAuthInfo().getValidPassword();

    // Get actual AuthInfo from demo data using hardcoded login
    DemoDataHelper.AuthInfo user = DemoDataHelper.getAuthInfoFromDb(validLogin);

    // Queries to clear the tables
    private static final String CLEAR_CARD_TRANSACTIONS = SQLQueries.getQuery("clear_card_transactions");
    private static final String CLEAR_CARDS = SQLQueries.getQuery("clear_cards");
    private static final String CLEAR_AUTH_CODES = SQLQueries.getQuery("clear_auth_codes");
    private static final String CLEAR_USERS = SQLQueries.getQuery("clear_users");

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

    // Method to clear all tables in the database
    @SneakyThrows
    public static void clearDatabase() {
        try (Connection conn = getConnection()) {
            QueryRunner runner = new QueryRunner();
            runner.update(conn, CLEAR_CARD_TRANSACTIONS);
            runner.update(conn, CLEAR_CARDS);
            runner.update(conn, CLEAR_AUTH_CODES);
            runner.update(conn, CLEAR_USERS);
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
