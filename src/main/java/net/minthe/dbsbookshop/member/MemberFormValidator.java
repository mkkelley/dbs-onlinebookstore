package net.minthe.dbsbookshop.member;

import net.minthe.dbsbookshop.util.CreditCardValidator;
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

        CreditCardValidator.validateCreditCard(
                errors,
                memberForm.getCreditcardnumber().trim(),
                memberForm.getCreditcardtype().trim(),
                "creditcardnumber",
                "creditcardtype");
    }
}
