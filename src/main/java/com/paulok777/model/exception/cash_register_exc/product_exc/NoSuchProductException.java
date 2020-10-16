package com.paulok777.model.exception.cash_register_exc.product_exc;

import com.paulok777.model.exception.cash_register_exc.OrderException;

public class NoSuchProductException extends OrderException {
    public NoSuchProductException(String message) {
        super(message);
    }
}
