package net.minthe.dbsbookshop.util;

import org.springframework.validation.Errors;

/**
 * Created by Michael Kelley on 10/28/2018
 */
public class CreditCardValidator {
    public static void validateCreditCard(Errors errors,
                                          String ccn,
                                          String ccType,
                                          String ccnField,
                                          String ccTypeField) {
        if (!ccn.isEmpty() &&
                !ccType.isEmpty()) {
            int checkDigit = checkDigitLuhn(errors, ccn);
            if (checkDigit != ccn.charAt(ccn.length() - 1) - '0') {
                errors.rejectValue(ccnField, "creditcardnumber.invalid");
            }

            if (!"amex".equals(ccType) &&
                    !"mc".equals(ccType) &&
                    !"visa".equals(ccType) &&
                    !"discover".equals(ccType)) {
                errors.rejectValue(ccTypeField, "creditcardtype.invalid");
            }
        } else if (ccType.isEmpty() && !ccn.isEmpty()) {
            errors.rejectValue(ccTypeField, "creditcardtype.notSpecified");
        } else if (!ccType.isEmpty()) { // implies ccn.isEmpty()
            errors.rejectValue(ccnField, "creditcardnumber.notSpecified");
        }

    }

    private static int checkDigitLuhn(Errors errors, String ccn) {
        int[] ccDigits = new int[ccn.length()];
        for (int i = ccn.length() - 1; i >= 0; --i) {
            if (!Character.isDigit(ccn.charAt(i))) {
                errors.rejectValue("creditcardnumber", "creditcardnumber.nonNumber");
                break;
            }
            ccDigits[ccn.length() - 1 - i] = ccn.charAt(i) - '0';
        }

        int sum = 0;
        for (int i = 1; i < ccDigits.length; ++i) {
            if (i % 2 == 1) {
                int doubled = ccDigits[i] * 2;
                if (doubled > 9) {
                    sum += doubled - 9;
                } else {
                    sum += doubled;
                }
            } else {
                sum += ccDigits[i];
            }
        }

        return (sum * 9) % 10;
    }
}
