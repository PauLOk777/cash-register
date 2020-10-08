package com.paulok777.model.exception.cash_register_exc.product_exc;

import com.paulok777.model.exception.cash_register_exc.ProductException;

public class NotEnoughProductsException extends ProductException {
    public NotEnoughProductsException(String message) {
        super(message);
    }
}
