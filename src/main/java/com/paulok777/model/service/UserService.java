package com.paulok777.model.service;

import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.registration_exc.DuplicateUsernameException;
//import com.paulok777.repository.UserRepository;
import com.paulok777.controller.util.ExceptionKeys;

public class UserService {
//    private final UserRepository userRepository;

    public void saveNewUser(UserDTO userDTO) {
        User user = new User(userDTO);
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        try {
//            userRepository.save(user);
//        } catch (Exception e) {
//            log.warn("(username: {}) {}.", SecurityContextHolder.getContext().getAuthentication().getName(),
//                    ExceptionKeys.DUPLICATE_USERNAME);
//            throw new DuplicateUsernameException(ExceptionKeys.DUPLICATE_USERNAME);
//        }
    }

    public User getCurrentUser() {
//        return userRepository.findByUsername(
//                SecurityContextHolder.getContext().getAuthentication().getName()
//        ).orElseThrow();
        return null;
    }
}