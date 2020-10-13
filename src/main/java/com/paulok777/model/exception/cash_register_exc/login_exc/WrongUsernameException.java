package com.paulok777.model.exception.cash_register_exc.login_exc;

import com.paulok777.model.exception.cash_register_exc.LoginException;

public class WrongUsernameException extends LoginException {
    public WrongUsernameException(String message) {
        super(message);
    }
}
