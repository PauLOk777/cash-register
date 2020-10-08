package com.paulok777.model.exception.cash_register_exc;

import com.paulok777.model.exception.CashRegisterException;

public class FieldValidationException extends CashRegisterException {
    public FieldValidationException(String message) {
        super(message);
    }
}
