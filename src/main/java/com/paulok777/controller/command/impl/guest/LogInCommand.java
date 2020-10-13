package com.paulok777.controller.command.impl.guest;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.CommandUtility;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongPasswordException;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongUsernameException;
import com.paulok777.model.service.UserService;
import com.paulok777.model.util.ExceptionKeys;

import javax.servlet.http.HttpServletRequest;

public class LogInCommand implements Command {
    private final UserService userService;
    private static final String DATA_ERR = "data_err";
    private static final String TRYING_LOGIN_TWICE = "log_twice";
    private static final String REDIRECT_USR_ERR = "redirect:/login?usr_err";
    private static final String REDIRECT_PASS_ERR = "redirect:/login?pass_err";
    private static final String REDIRECT_LOG_TWICE_ERR = "redirect:/login?log_twice_err";
    private static final String REDIRECT_CASHIER = "redirect:/cashier/orders";
    private static final String REDIRECT_SENIOR_CASHIER = "redirect:/senior_cashier/orders";
    private static final String REDIRECT_COMMODITY_EXPERT = "redirect:/commodity_expert/products";

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        User.Role role;
        try {
            role = userService.checkUserAndGetRole(username, pass);
        } catch (WrongUsernameException e) {
            request.setAttribute(DATA_ERR, e.getMessage());
            return REDIRECT_USR_ERR;
        } catch (WrongPasswordException e) {
            request.setAttribute(DATA_ERR, e.getMessage());
            return REDIRECT_PASS_ERR;
        }

        if (CommandUtility.checkUserIsLogged(request, username)) {
            request.setAttribute(TRYING_LOGIN_TWICE, ExceptionKeys.TRYING_LOGIN_TWICE);
            return REDIRECT_LOG_TWICE_ERR;
        }
        CommandUtility.setUserRole(request, role, username);

        switch (role) {
            case SENIOR_CASHIER:
                return REDIRECT_SENIOR_CASHIER;
            case CASHIER:
                return REDIRECT_CASHIER;
            default:
                return REDIRECT_COMMODITY_EXPERT;
        }
    }
}
