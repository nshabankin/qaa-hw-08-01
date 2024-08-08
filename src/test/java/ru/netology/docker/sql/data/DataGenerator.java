package ru.netology.docker.sql.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.util.Locale;
import java.util.UUID;

/**
 * DataGenerator is a utility class designed to generate random data
 * for testing purposes, such as user authentication info, authorization codes,
 * card information, and transaction details.
 */
public class DataGenerator {

    // Prevents instantiation of the class
    private DataGenerator() {
    }

    // Initialize Faker for generating random data in English locale
    private static final Faker faker = new Faker(new Locale("en"));

    // Generate random values for testing data
    private static final String randomUserId = UUID.randomUUID().toString();
    private static final String randomLogin = faker.name().username();
    private static final String randomPassword = faker.internet().password();
    private static final String randomAuthCodeId = UUID.randomUUID().toString();
    private static final String randomAuthCode = faker.number().digits(6);
    private static final String randomCard1Id = UUID.randomUUID().toString();
    private static final String randomCard1Number = faker.finance().creditCard().replace("-", " ");
    private static final int randomBalanceInKopecks1 = faker.number().numberBetween(1000, 100000);
    private static final String randomCard2Id = UUID.randomUUID().toString();
    private static final String randomCard2Number = faker.finance().creditCard().replace("-", " ");
    private static final int randomBalanceInKopecks2 = faker.number().numberBetween(1000, 100000);
    public static final String randomTransactionId = UUID.randomUUID().toString();
    private static final int randomAmountInKopecks = faker.number().numberBetween(1000, 100000);

    /**
     * AuthInfo is an immutable class that holds user authentication information.
     */
    @Value
    public static class AuthInfo {
        String userId;
        String login;
        String password;
        String status;
    }

    /**
     * Generates random authentication information for a user.
     *
     * @return a new instance of AuthInfo with random values
     */
    public static AuthInfo generateAuthInfo() {
        return new AuthInfo(
                randomUserId,
                randomLogin,
                randomPassword,
                "active"
        );
    }

    /**
     * AuthCode is an immutable class that holds user authorization code information.
     */
    @Value
    public static class AuthCode {
        String authCodeId;
        String userId;
        String authCode;
    }

    /**
     * Generates a random authorization code for a given user ID.
     *
     * @param userId the user ID for which to generate the authorization code
     * @return a new instance of AuthCode with random values
     */
    public static AuthCode generateAuthCode(String userId) {
        return new AuthCode(
                randomAuthCodeId,
                userId,
                randomAuthCode
        );
    }

    /**
     * Card1Info is an immutable class that holds information about the first card.
     */
    @Value
    public static class Card1Info {
        String card1Id;
        String userId;
        String card1Number;
        int balanceInKopecks1;
    }

    /**
     * Generates random information for the first card associated with a given user ID.
     *
     * @param userId the user ID for which to generate the card information
     * @return a new instance of Card1Info with random values
     */
    public static Card1Info generateCard1Info(String userId) {
        return new Card1Info(
                randomCard1Id,
                userId,
                randomCard1Number,
                randomBalanceInKopecks1
        );
    }

    /**
     * Card2Info is an immutable class that holds information about the second card.
     */
    @Value
    public static class Card2Info {
        String card2Id;
        String userId;
        String card2Number;
        int balanceInKopecks2;
    }

    /**
     * Generates random information for the second card associated with a given user ID.
     *
     * @param userId the user ID for which to generate the card information
     * @return a new instance of Card2Info with random values
     */
    public static Card2Info generateCard2Info(String userId) {
        return new Card2Info(
                randomCard2Id,
                userId,
                randomCard2Number,
                randomBalanceInKopecks2
        );
    }

    /**
     * TransactionInfo is an immutable class that holds information about a card transaction.
     */
    @Value
    public static class TransactionInfo {
        String transactionId;
        String source;
        String target;
        int amountInKopecks;
    }

    /**
     * Generates random information for a card transaction between two cards.
     *
     * @param source the ID of the source card
     * @param target the ID of the target card
     * @return a new instance of TransactionInfo with random values
     */
    public static TransactionInfo generateTransactionInfo(String source, String target) {
        return new TransactionInfo(
                randomTransactionId,
                source,
                target,
                randomAmountInKopecks
        );
    }
}
