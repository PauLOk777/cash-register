package com.paulok777.controller.command.impl.guest;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.util.Validator;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.service.UserService;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;

    public RegistrationCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute(HttpServletRequest request) {
        UserDTO userDTO = UserDTO.builder()
                .firstName(request.getParameter("firstName"))
                .lastName(request.getParameter("lastName"))
                .username(request.getParameter("username"))
                .password(request.getParameter("password"))
                .email(request.getParameter("email"))
                .phoneNumber(request.getParameter("phoneNumber"))
                .role(request.getParameter("role"))
                .build();

        Validator.validateUser(userDTO, request.getParameter("anonymous"));
        userService.saveNewUser(userDTO);
        return "redirect:/login";
    }
}
