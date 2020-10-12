package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class GetLoginPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/login.jsp";
    }
}
