package net.minthe.dbsbookshop.model;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public class OrderSubmissionValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return OrderSubmission.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        OrderSubmission orderSubmission = (OrderSubmission) o;
        if (orderSubmission.isOneClick()) {
            return;
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipCity", "shipCity.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipState", "shipState.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipAddress", "shipAddress.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipCity", "shipCity.empty");
        if (orderSubmission.isNewCc()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCcn", "newCcn.empty");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCcType", "newCcType.empty");
        }

        if (orderSubmission.getShipZip() < 10000 ||
                orderSubmission.getShipZip() > 99999) {
            errors.rejectValue("shipZip", "shipZip.invalid");
        }
    }
}
