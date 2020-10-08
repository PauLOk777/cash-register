package com.paulok777.model.exception.cash_register_exc.product_exc;

import com.paulok777.model.exception.cash_register_exc.ProductException;

public class DuplicateCodeOrNameException extends ProductException {
    public DuplicateCodeOrNameException(String message) {
        super(message);
    }
}
