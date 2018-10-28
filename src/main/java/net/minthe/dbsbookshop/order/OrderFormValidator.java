package net.minthe.dbsbookshop.order;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by Michael Kelley on 10/26/2018
 */
public class OrderFormValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return OrderForm.class.equals(aClass);
    }


    @Override
    public void validate(Object o, Errors errors) {
        OrderForm orderForm = (OrderForm) o;
        if (orderForm.isOneClick()) {
            return;
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipCity", "shipCity.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipState", "shipState.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipAddress", "shipAddress.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "shipCity", "shipCity.empty");
        if (orderForm.isNewCc()) {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCcn", "newCcn.empty");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newCcType", "newCcType.empty");
        }

        if (orderForm.getShipZip() < 10000 ||
                orderForm.getShipZip() > 99999) {
            errors.rejectValue("shipZip", "shipZip.invalid");
        }
    }
}
