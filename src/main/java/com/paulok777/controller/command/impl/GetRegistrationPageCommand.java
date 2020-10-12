package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class GetRegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/registration.jsp";
    }
}
