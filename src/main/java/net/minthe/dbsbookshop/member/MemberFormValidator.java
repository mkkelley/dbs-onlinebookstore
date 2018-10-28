package net.minthe.dbsbookshop.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * Created by Michael Kelley on 10/28/2018
 */
@Component
public class MemberFormValidator implements Validator {
    private final MemberRepository memberRepository;

    @Autowired
    public MemberFormValidator(MemberRepository memberRepository) {this.memberRepository = memberRepository;}

    @Override
    public boolean supports(Class<?> clazz) {
        return MemberForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MemberForm memberForm = (MemberForm) target;

        rejectIfEmptyOrWhitespace(errors, "fname", "fname.empty");
        rejectIfEmptyOrWhitespace(errors, "lname", "lname.empty");
        rejectIfEmptyOrWhitespace(errors, "address", "address.empty");
        rejectIfEmptyOrWhitespace(errors, "city", "city.empty");
        rejectIfEmptyOrWhitespace(errors, "state", "state.empty");

        if (memberForm.getZip() < 10000 || memberForm.getZip() > 99999) {
            errors.rejectValue("zip", "zip.invalid");
        }

        rejectIfEmptyOrWhitespace(errors, "userid", "userid.empty");

        if (memberRepository.findByUserid(memberForm.getUserid()).isPresent()) {
            errors.rejectValue("userid", "userid.exists");
        }

        rejectIfEmptyOrWhitespace(errors, "password", "password.empty");

        if (!memberForm.getCreditcardtype().trim().isEmpty() &&
                !memberForm.getCreditcardnumber().trim().isEmpty()) {
            String ccn = memberForm.getCreditcardnumber();
            int[] ccDigits = new int[ccn.length()];
            int checkDigit = checkDigitLuhn(errors, ccn, ccDigits);
            if (checkDigit != ccDigits[0]) {
                errors.rejectValue("creditcardnumber", "creditcardnumber.invalid");
            }

            String type = memberForm.getCreditcardtype();
            if (!"amex".equals(type) &&
                    !"mc".equals(type) &&
                    !"visa".equals(type) &&
                    !"discover".equals(type)) {
                errors.rejectValue("creditcardtype", "creditcardtype.invalid");
            }
        } else if (memberForm.getCreditcardtype().trim().isEmpty() &&
                memberForm.getCreditcardnumber().trim().isEmpty()) {
        } else if (memberForm.getCreditcardtype().trim().isEmpty()) {
            errors.rejectValue("creditcardnumber", "creditcardnumber.notSpecified");
        } else if (memberForm.getCreditcardnumber().trim().isEmpty()) {
            errors.rejectValue("creditcardtype", "creditcardtype.notSpecified");
        }
    }

    private int checkDigitLuhn(Errors errors, String ccn, int[] ccDigits) {
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
