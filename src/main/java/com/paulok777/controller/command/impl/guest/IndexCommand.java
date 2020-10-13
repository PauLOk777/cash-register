package com.paulok777.controller.command.impl.guest;

import com.paulok777.controller.command.Command;

import javax.servlet.http.HttpServletRequest;

public class IndexCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return "/index.jsp";
    }
}
