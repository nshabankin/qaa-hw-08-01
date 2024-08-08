package ru.netology.docker.sql.test;

import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import ru.netology.docker.sql.data.DataGenerator;
import ru.netology.docker.sql.data.DataGenerator.AuthInfo;
import ru.netology.docker.sql.data.DataGenerator.AuthCode;
import ru.netology.docker.sql.util.SQLQueries;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

public class InsertIntoDBTest {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/app";
    private static final String DB_USER = "app";
    private static final String DB_PASS = "pass";

    private static Connection connection; // Connection object to interact with the database
    private static AuthInfo testUser; // Static variable to store the test user information

    // Method to set up the database connection and load SQL queries before all tests run
    @BeforeAll
    @SneakyThrows
    public static void setUpAll() {
        connection = DriverManager.getConnection(
                DB_URL,
                DB_USER,
                DB_PASS); // Establish the database connection

        testUser = DataGenerator.generateAuthInfo(); // Generate new user data
        insertUser(testUser); // Insert new user into the database
    }

    // Method to clear and close the database after all tests
    @AfterAll
    @SneakyThrows
    public static void clearDB() {
        clearDatabase();
        if (connection != null && !connection.isClosed()) {
            connection.close(); // Close the database connection
        }
    }

    // Method to insert a new user into the database
    @SneakyThrows
    private static void insertUser(AuthInfo authInfo) {
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("insert_user"))) {
            pstmt.setString(1, authInfo.getUserId()); // Set user ID parameter
            pstmt.setString(2, authInfo.getLogin()); // Set login parameter
            pstmt.setString(3, authInfo.getPassword()); // Set password parameter
            pstmt.setString(4, authInfo.getStatus()); // Set status parameter
            pstmt.executeUpdate(); // Execute the update to insert the user
        }
    }

    // Method to clear all tables in the database
    @SneakyThrows
    private static void clearDatabase() {
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(SQLQueries.getQuery("clear_card_transactions"));
            stmt.executeUpdate(SQLQueries.getQuery("clear_cards"));
            stmt.executeUpdate(SQLQueries.getQuery("clear_auth_codes"));
            stmt.executeUpdate(SQLQueries.getQuery("clear_users"));
        }
    }

    // Test method to insert a new authentication code into the database
    @Test
    @SneakyThrows
    public void shouldInsertAuthCode() {
        AuthCode authCode = DataGenerator.generateAuthCode(
                testUser.getUserId()); // Generate new auth code for the test user

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("insert_auth_code"))) {
            pstmt.setString(1, authCode.getAuthCodeId()); // Set auth code ID parameter
            pstmt.setString(2, authCode.getUserId()); // Set user ID parameter
            pstmt.setString(3, authCode.getAuthCode()); // Set auth code parameter
            int affectedRows = pstmt.executeUpdate(); // Execute the update to insert the auth code
            assertEquals(1, affectedRows, "One row should be inserted."); // Assert that one row was inserted
        }

        // Verify that the auth code was correctly inserted
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("select_auth_code_by_id"))) {
            pstmt.setString(1, authCode.getAuthCodeId()); // Set auth code ID parameter
            ResultSet rs = pstmt.executeQuery(); // Execute the query to select the auth code by ID
            assertTrue(rs.next(), "Inserted auth code should be found."); // Assert that the auth code was found
            assertEquals(authCode.getAuthCodeId(), rs.getString("id")); // Assert that the auth code ID matches
            assertEquals(authCode.getUserId(), rs.getString("user_id")); // Assert that the user ID matches
            assertEquals(authCode.getAuthCode(), rs.getString("code")); // Assert that the auth code matches
        }
    }

    // Test method to insert a new first card into the database
    @Test
    @SneakyThrows
    public void shouldInsertCard1() {
        DataGenerator.Card1Info card1Info = DataGenerator.generateCard1Info(
                testUser.getUserId()); // Generate new card data for the test user

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("insert_card1"))) {
            pstmt.setString(1, card1Info.getCard1Id()); // Set card ID parameter
            pstmt.setString(2, card1Info.getUserId()); // Set user ID parameter
            pstmt.setString(3, card1Info.getCard1Number()); // Set card number parameter
            pstmt.setInt(4, card1Info.getBalanceInKopecks1()); // Set balance in kopecks parameter
            int affectedRows = pstmt.executeUpdate(); // Execute the update to insert the card
            assertEquals(1, affectedRows, "One row should be inserted."); // Assert that one row was inserted
        }

        // Verify that the card was correctly inserted
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("select_card1_by_id"))) {
            pstmt.setString(1, card1Info.getCard1Id()); // Set card ID parameter
            ResultSet rs = pstmt.executeQuery(); // Execute the query to select the card by ID
            assertTrue(rs.next(), "Inserted card should be found."); // Assert that the card was found
            assertEquals(card1Info.getCard1Id(), rs.getString("id")); // Assert that the card ID matches
            assertEquals(card1Info.getUserId(), rs.getString("user_id")); // Assert that the user ID matches
            assertEquals(card1Info.getCard1Number(), rs.getString("number")); // Assert that the card number matches
            assertEquals(card1Info.getBalanceInKopecks1(), rs.getInt("balance_in_kopecks")); // Assert that the balance matches
        }
    }

    // Test method to insert a new second card into the database
    @Test
    @SneakyThrows
    public void shouldInsertCard2() {

        DataGenerator.Card2Info card2Info = DataGenerator.generateCard2Info(
                testUser.getUserId()); // Generate new card data for the test user

        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("insert_card2"))) {
            pstmt.setString(1, card2Info.getCard2Id()); // Set card ID parameter
            pstmt.setString(2, card2Info.getUserId()); // Set user ID parameter
            pstmt.setString(3, card2Info.getCard2Number()); // Set card number parameter
            pstmt.setInt(4, card2Info.getBalanceInKopecks2()); // Set balance in kopecks parameter
            int affectedRows = pstmt.executeUpdate(); // Execute the update to insert the card
            assertEquals(1, affectedRows, "One row should be inserted."); // Assert that one row was inserted
        }

        // Verify that the card was correctly inserted
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("select_card2_by_id"))) {
            pstmt.setString(1, card2Info.getCard2Id()); // Set card ID parameter
            ResultSet rs = pstmt.executeQuery(); // Execute the query to select the card by ID
            assertTrue(rs.next(), "Inserted card should be found."); // Assert that the card was found
            assertEquals(card2Info.getCard2Id(), rs.getString("id")); // Assert that the card ID matches
            assertEquals(card2Info.getUserId(), rs.getString("user_id")); // Assert that the user ID matches
            assertEquals(card2Info.getCard2Number(), rs.getString("number")); // Assert that the card number matches
            assertEquals(card2Info.getBalanceInKopecks2(), rs.getInt("balance_in_kopecks")); // Assert that the balance matches
        }
    }


    // Test method to insert a new card transaction into the database
    @Test
    @SneakyThrows
    @Disabled
    public void shouldInsertCardTransaction() {

        DataGenerator.Card1Info sourceCardInfo = DataGenerator.generateCard1Info(
                testUser.getUserId()); // Generate source card for the test user
        DataGenerator.Card2Info targetCardInfo = DataGenerator.generateCard2Info(
                testUser.getUserId()); // Generate target card for the test user

        DataGenerator.TransactionInfo transactionInfo = DataGenerator.generateTransactionInfo(
                sourceCardInfo.getCard1Id(),
                targetCardInfo.getCard2Id()); // Generate new card transaction

        // Insert the card transaction into the database
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("insert_card_transaction"))) {
            pstmt.setString(1, transactionInfo.getTransactionId()); // Set transaction ID parameter
            pstmt.setString(2, transactionInfo.getSource()); // Set source card ID parameter
            pstmt.setString(3, transactionInfo.getTarget()); // Set target card ID parameter
            pstmt.setInt(4, transactionInfo.getAmountInKopecks()); // Set amount in kopecks parameter
            int affectedRows = pstmt.executeUpdate(); // Execute the update to insert the card transaction
            assertEquals(1, affectedRows, "One row should be inserted."); // Assert that one row was inserted
        }

        // Verify that the card transaction was correctly inserted
        try (PreparedStatement pstmt = connection.prepareStatement(
                SQLQueries.getQuery("select_card_transaction_by_id"))) {
            pstmt.setString(1, transactionInfo.getTransactionId()); // Set transaction ID parameter
            ResultSet rs = pstmt.executeQuery(); // Execute the query to select the card transaction by ID
            assertTrue(rs.next(), "Inserted card transaction should be found."); // Assert that the card transaction was found
            assertEquals(transactionInfo.getTransactionId(), rs.getString("id")); // Assert that the transaction ID matches
            assertEquals(transactionInfo.getSource(), rs.getString("source")); // Assert that the source card ID matches
            assertEquals(transactionInfo.getTarget(), rs.getString("target")); // Assert that the target card ID matches
            assertEquals(transactionInfo.getAmountInKopecks(), rs.getInt("amount_in_kopecks")); // Assert that the amount in kopecks matches
        }
    }
}
