package ru.netology.docker.sql.data;

import lombok.Value;
import lombok.SneakyThrows;
import ru.netology.docker.sql.util.SQLQueries;

import java.sql.*;

public class DemoDataHelper {

    private DemoDataHelper() {
    }

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "pass";

    @Value
    public static class AuthInfo {
        String userId;
        String login;
        String password;
        String status;
    }

    /**
     * Fetches AuthInfo for a user with the specified login.
     *
     * @param login the login of the user
     * @return AuthInfo object containing user details
     */
    @SneakyThrows
    public static AuthInfo getAuthInfoFromDb(String login) {
        // Establish a connection to the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             // Create a PreparedStatement to execute the SQL query with the specified login
             PreparedStatement pstmt = connection.prepareStatement(
                     SQLQueries.getQuery("select_user_by_login"))) {

            // Set the login parameter in the SQL query
            pstmt.setString(1, login);

            // Execute the query and obtain the result set
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if the result set has a row
                if (rs.next()) {
                    // Return an AuthInfo object populated with data from the result set
                    return new AuthInfo(
                            rs.getString("id"),
                            rs.getString("login"),
                            rs.getString("password"),
                            rs.getString("status"));
                }
            }
        }
        // Throw an exception if no users are found with the specified login
        throw new RuntimeException("No users found with login: " + login);
    }

    @Value
    public static class AuthCode {
        String authCodeId;
        String userId;
        String code;
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
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             // Create a PreparedStatement to execute the SQL query with the specified userId
             PreparedStatement pstmt = connection.prepareStatement(
                     SQLQueries.getQuery("select_latest_auth_code"))) {

            // Set the userId parameter in the SQL query
            pstmt.setString(1, userId);

            // Execute the query and obtain the result set
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if the result set has a row
                if (rs.next()) {
                    // Return an AuthCode object populated with data from the result set
                    return new AuthCode(
                            rs.getString("id"),
                            rs.getString("user_id"),
                            rs.getString("code"));
                }
            }
        }
        // Throw an exception if no auth codes are found for the specified userId
        throw new RuntimeException("No auth codes found for user: " + userId);
    }

    @Value
    public static class CardInfo {
        String cardId;
        String userId;
        String cardNumber;
        int balanceInKopecks;
    }

    /**
     * Fetches CardInfo for the specified user.
     *
     * @param userId the ID of the user
     * @return CardInfo object containing card details for the user
     */
    @SneakyThrows
    public static CardInfo getCardInfoFromDb(String userId) {
        // Establish a connection to the database
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             // Create a PreparedStatement to execute the SQL query with the specified userId
             PreparedStatement pstmt = connection.prepareStatement(
                     SQLQueries.getQuery("select_card_by_user_id"))) {

            // Set the userId parameter in the SQL query
            pstmt.setString(1, userId);

            // Execute the query and obtain the result set
            try (ResultSet rs = pstmt.executeQuery()) {
                // Check if the result set has a row
                if (rs.next()) {
                    // Return a CardInfo object populated with data from the result set
                    return new CardInfo(
                            rs.getString("id"),
                            rs.getString("user_id"),
                            rs.getString("number"),
                            rs.getInt("balance_in_kopecks"));
                }
            }
        }
        // Throw an exception if no cards are found for the specified userId
        throw new RuntimeException("No cards found for user: " + userId);
    }
}
