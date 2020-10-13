package com.paulok777.controller.command.impl;

import com.paulok777.controller.command.Command;
import com.paulok777.model.entity.User;
import com.paulok777.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class GetRegistrationPageCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.setAttribute("positions", Arrays.stream(User.Role.values())
                .filter((role -> !"UNKNOWN".equals(role.name())))
                .collect(Collectors.toList()));
        return "/registration.jsp";
    }
}
