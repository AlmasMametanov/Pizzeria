package com.epam.pizzeria.validator;

public class Validator {
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,15}$";
    private static final String PHONE_NUMBER_REGEX = "^(\\+7|8)\\d{10}$";
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z+.]+$";
    private static final String CARD_NUMBER_REGEX = "^\\d{16}$";
    private static final String VALIDITY_OF_BANK_CARD_REGEX = "^(0[1-9]|1[012])['/']\\d{2}$";
    private static final String CVC_OF_BANK_CARD_REGEX = "^\\d{3}$";

    public static boolean validateEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean validatePassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber.matches(PHONE_NUMBER_REGEX);
    }

    public static boolean validateCardNumber(String cardNumber) {
        return cardNumber.matches(CARD_NUMBER_REGEX);
    }

    public static boolean validateValidityOfBankCard(String validityOfBankCard) {
        return validityOfBankCard.matches(VALIDITY_OF_BANK_CARD_REGEX);
    }

    public static boolean validateCVCOfBankCard(String cvc) {
        return cvc.matches(CVC_OF_BANK_CARD_REGEX);
    }
}
