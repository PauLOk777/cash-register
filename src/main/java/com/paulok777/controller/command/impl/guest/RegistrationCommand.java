package com.paulok777.controller.command.impl.guest;

import com.paulok777.controller.command.Command;
import com.paulok777.controller.util.Validator;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

public class RegistrationCommand implements Command {
    private final UserService userService;
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

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

        logger.debug("UserDTO for registration: {}", userDTO);

        Validator.validateUser(userDTO);
        userService.saveNewUser(userDTO);
        return "redirect:/login";
    }
}
