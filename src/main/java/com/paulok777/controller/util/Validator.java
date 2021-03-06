package com.paulok777.controller.util;

import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.exception.cash_register_exc.FieldValidationException;
import com.paulok777.model.util.ExceptionKeys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Validator {
    private static final Logger logger = LogManager.getLogger(Validator.class);

    public static void validateUser(UserDTO userDTO) {
        if (!userDTO.getFirstName().matches(ValidationRegex.FIRST_NAME_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_FIRST_NAME);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_FIRST_NAME);
        }

        if (!userDTO.getLastName().matches(ValidationRegex.LAST_NAME_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_LAST_NAME);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_LAST_NAME);
        }

        if (!userDTO.getEmail().matches(ValidationRegex.EMAIL_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_EMAIL);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_EMAIL);
        }

        if (!userDTO.getUsername().matches(ValidationRegex.USERNAME_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_USERNAME);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_USERNAME);
        }

        if (!userDTO.getPassword().matches(ValidationRegex.PASSWORD_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_PASSWORD);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_PASSWORD);
        }

        if (!userDTO.getPhoneNumber().matches(ValidationRegex.PHONE_NUMBER_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_PHONE_NUMBER);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_PHONE_NUMBER);
        }

        if (!userDTO.getRole().matches(ValidationRegex.POSITION_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_USER_POSITION);
            throw new FieldValidationException(ExceptionKeys.INVALID_USER_POSITION);
        }
    }

    public static void validateProduct(ProductDTO productDTO) {

        if (!productDTO.getCode().matches(ValidationRegex.CODE_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_PRODUCT_CODE);
            throw new FieldValidationException(ExceptionKeys.INVALID_PRODUCT_CODE);
        }

        if (!productDTO.getName().trim().matches(ValidationRegex.PRODUCT_NAME_REGEX)) {
            logger.error("{}.", ExceptionKeys.INVALID_PRODUCT_NAME);
            throw new FieldValidationException(ExceptionKeys.INVALID_PRODUCT_NAME);
        }

        if (productDTO.getPrice() < 1) {
            logger.error("{}.", ExceptionKeys.INVALID_PRODUCT_PRICE);
            throw new FieldValidationException(ExceptionKeys.INVALID_PRODUCT_PRICE);
        }

        validateAmountForCommodityExpert(productDTO.getAmount());

        if (!productDTO.getMeasure().matches(ValidationRegex.MEASURE_REGEXP)) {
            logger.error("{}.", ExceptionKeys.INVALID_PRODUCT_MEASURE);
            throw new FieldValidationException(ExceptionKeys.INVALID_PRODUCT_MEASURE);
        }
    }

    public static void validateAmountForCommodityExpert(Long amount) {
        if (amount < 0) {
            logger.error("{}.", ExceptionKeys.INVALID_AMOUNT_COMMODITY_EXPERT);
            throw new FieldValidationException(ExceptionKeys.INVALID_AMOUNT_COMMODITY_EXPERT);
        }
    }

    public static void validateAmountForCashier(Long amount) {
        if (amount < 1) {
            logger.error("{}.", ExceptionKeys.INVALID_AMOUNT_CASHIER);
            throw new FieldValidationException(ExceptionKeys.INVALID_AMOUNT_CASHIER);
        }
    }
}
