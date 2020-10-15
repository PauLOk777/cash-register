package com.paulok777.model.service;

import com.paulok777.controller.util.PasswordEncoder;
import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.UserDao;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongPasswordException;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongUsernameException;
import com.paulok777.model.exception.cash_register_exc.registration_exc.DuplicateUsernameException;
import com.paulok777.model.util.ExceptionKeys;

public class UserService {
    private final DaoFactory daoFactory;
    private final PasswordEncoder passwordEncoder;

    public UserService(DaoFactory daoFactory, PasswordEncoder passwordEncoder) {
        this.daoFactory = daoFactory;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveNewUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try (UserDao userDao = daoFactory.createUserDao()){
            userDao.create(user);
        } catch (Exception e) {
//            log.warn("{}.", ExceptionKeys.DUPLICATE_USERNAME);
            e.printStackTrace();
            throw new DuplicateUsernameException(ExceptionKeys.DUPLICATE_USERNAME);
        }
    }

    public User getUserByUsername(String username) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByUsername(username)
                    .orElseThrow(RuntimeException::new);
        }
    }

    public User.Role checkUserAndGetRole(String username, String password) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> new WrongUsernameException(ExceptionKeys.WRONG_USERNAME));
            if (!user.getPassword().equals(passwordEncoder.encode(password))) {
                throw new WrongPasswordException(ExceptionKeys.WRONG_PASSWORD);
            }
            return user.getRole();
        }
    }
}