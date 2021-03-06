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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The User service.
 */
public class UserService {
    private final DaoFactory daoFactory;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LogManager.getLogger(UserService.class);

    /**
     * Instantiates a new User service.
     *
     * @param daoFactory      the dao factory
     * @param passwordEncoder the password encoder
     */
    public UserService(DaoFactory daoFactory, PasswordEncoder passwordEncoder) {
        this.daoFactory = daoFactory;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Save new user.
     *
     * @param userDTO the user dto
     */
    public void saveNewUser(UserDTO userDTO) {
        User user = new User(userDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try (UserDao userDao = daoFactory.createUserDao()) {
            userDao.create(user);
        } catch (Exception e) {
            logger.error("{}.", ExceptionKeys.DUPLICATE_USERNAME);
            throw new DuplicateUsernameException(ExceptionKeys.DUPLICATE_USERNAME);
        }
    }

    /**
     * Gets user by username.
     *
     * @param username the username
     * @return the user
     */
    public User getUserByUsername(String username) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            return userDao.findByUsername(username)
                    .orElseThrow(() -> {
                        logger.error("Can't find user by username: {}", username);
                        throw new RuntimeException();
                    });
        }
    }

    /**
     * Check if user exists and get role of user.
     *
     * @param username the username
     * @param password the password
     * @return the role of user
     */
    public User.Role checkUserAndGetRole(String username, String password) {
        try (UserDao userDao = daoFactory.createUserDao()) {
            User user = userDao.findByUsername(username)
                    .orElseThrow(() -> {
                        logger.error("Wrong username");
                        throw new WrongUsernameException(ExceptionKeys.WRONG_USERNAME);
                    });
            if (!user.getPassword().equals(passwordEncoder.encode(password))) {
                logger.error("Wrong password");
                throw new WrongPasswordException(ExceptionKeys.WRONG_PASSWORD);
            }
            return user.getRole();
        }
    }
}