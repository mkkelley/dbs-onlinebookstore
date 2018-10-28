package net.minthe.dbsbookshop.member;

import net.minthe.dbsbookshop.util.CreditCardValidator;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

/**
 * Created by Michael Kelley on 10/28/2018
 */
public class MemberValidator implements Validator {
    private final MemberRepository memberRepository;

    public MemberValidator(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Member.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Member member = (Member) target;

        rejectIfEmptyOrWhitespace(errors, "fname", "fname.empty");
        rejectIfEmptyOrWhitespace(errors, "lname", "lname.empty");
        rejectIfEmptyOrWhitespace(errors, "address", "address.empty");
        rejectIfEmptyOrWhitespace(errors, "city", "city.empty");
        rejectIfEmptyOrWhitespace(errors, "state", "state.empty");

        if (member.getZip() < 10000 || member.getZip() > 99999) {
            errors.rejectValue("zip", "zip.invalid");
        }

        rejectIfEmptyOrWhitespace(errors, "userid", "userid.empty");

        if (!memberRepository.findByUserid(member.getUserid()).isPresent()) {
            errors.rejectValue("userid", "userid.notexists");
        }

        rejectIfEmptyOrWhitespace(errors, "password", "password.empty");

        CreditCardValidator.validateCreditCard(
                errors,
                member.getCreditcardnumber().trim(),
                member.getCreditcardtype().trim(),
                "creditcardnumber",
                "creditcardtype");

    }
}
