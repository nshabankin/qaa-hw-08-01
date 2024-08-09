package ru.netology.docker.sql.data;

import lombok.Value;
import lombok.SneakyThrows;
import ru.netology.docker.sql.util.SQLQueries;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Map;

public class DemoDataHelper {

    private DemoDataHelper() {
    }

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "pass";

    // SQL Queries
    private static final String SELECT_USER_BY_LOGIN = SQLQueries.getQuery("select_user_by_login");
    private static final String SELECT_LATEST_AUTH_CODE_BY_USER_ID = SQLQueries.getQuery("select_latest_auth_code_by_user_id");

    // Utility method to establish a database connection
    @SneakyThrows
    public static Connection getConnection() {
        return DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASS
        );
    }

    // Valid user AuthInfo class
    @Value
    public static class ValidAuthInfo {
        String validLogin;
        String validPassword;
    }

    // Method to retrieve the valid AuthInfo
    public static ValidAuthInfo getValidAuthInfo() {
        // Hardcoded valid user credentials
        return new ValidAuthInfo("petya", "123qwerty");
    }

    // AuthInfo class to represent user information
    @Value
    public static class AuthInfo {
        String id;
        String login;
        String password;
        String status;
    }

    /**
     * Fetches AuthInfo for an existing user with the specified login.
     *
     * @param login the login of the user
     * @return AuthInfo object containing user details
     */
    @SneakyThrows
    public static AuthInfo getAuthInfoFromDb(String login) {

        // Establish a connection to the database
        try (Connection connection = getConnection()) {

            // Create a QueryRunner
            QueryRunner runner = new QueryRunner();

            // Use MapHandler to get a map of column names to values
            Map<String, Object> result = runner.query(connection, SELECT_USER_BY_LOGIN, new MapHandler(), login);
            if (result != null) {
                return new AuthInfo(
                        (String) result.get("id"),
                        (String) result.get("login"),
                        (String) result.get("password"),
                        (String) result.get("status")
                );
            }
            return null;
        }
    }

    // AuthCode class to represent authentication code
    @Value
    public static class AuthCode {
        String id;
        String user_id;
        String code;
        java.sql.Timestamp created;
    }

    /**
     * Fetches the latest AuthCode for the specified user.
     *
     * @param userId the ID of the user
     * @return AuthCode object containing the latest auth code for the user
     */
    @SneakyThrows
    public static AuthCode getAuthCodeFromDb(String userId) {

        // Establish a connection to the database
        try (Connection connection = getConnection()) {

            // Create a QueryRunner
            QueryRunner runner = new QueryRunner();

            // Use MapHandler to get a map of column names to values
            Map<String, Object> result = runner.query(connection, SELECT_LATEST_AUTH_CODE_BY_USER_ID, new MapHandler(), userId);
            if (result != null) {
                return new AuthCode(
                        (String) result.get("id"),
                        (String) result.get("user_id"),
                        (String) result.get("code"),
                        (java.sql.Timestamp) result.get("created")
                );
            }
            return null;
        }
    }
}