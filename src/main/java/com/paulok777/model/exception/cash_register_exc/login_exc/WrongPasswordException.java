package com.paulok777.model.exception.cash_register_exc.login_exc;

import com.paulok777.model.exception.cash_register_exc.LoginException;

public class WrongPasswordException extends LoginException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
