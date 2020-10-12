package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;
import com.paulok777.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class LogInCommand implements Command {
    private final UserService userService;

    public LogInCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
