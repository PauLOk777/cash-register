package com.paulok777.controller.command.impl.guest;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.command.impl.CommandUtility;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongPasswordException;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongUsernameException;
import com.paulok777.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class LogInCommand implements Command {
    private final UserService userService;
    private static final String REDIRECT_USR_ERR = "redirect:/login?usr_err";
    private static final String REDIRECT_PASS_ERR = "redirect:/login?pass_err";
    private static final String REDIRECT_LOG_TWICE_ERR = "redirect:/login?log_twice_err";
    private static final String REDIRECT_CASHIER = "redirect:/cashier/orders";
    private static final String REDIRECT_SENIOR_CASHIER = "redirect:/senior_cashier/orders";
    private static final String REDIRECT_COMMODITY_EXPERT = "redirect:/commodity_expert/products";
    private static final Logger logger = LogManager.getLogger(LogInCommand.class);

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String username = request.getParameter("username");
        String pass = request.getParameter("password");
        logger.info("User try to login: username: {}, pass: {}", username, pass);
        User.Role role;
        try {
            role = userService.checkUserAndGetRole(username, pass);
        } catch (WrongUsernameException e) {
            logger.warn("Wrong username");
            return REDIRECT_USR_ERR;
        } catch (WrongPasswordException e) {
            logger.warn("Wrong password");
            return REDIRECT_PASS_ERR;
        }

        if (CommandUtility.checkUserIsLogged(request, username)) {
            logger.warn("User is logged");
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
