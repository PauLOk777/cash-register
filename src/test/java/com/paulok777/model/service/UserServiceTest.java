package com.paulok777.model.service;

import com.paulok777.controller.util.MD5PasswordEncoder;
import com.paulok777.controller.util.PasswordEncoder;
import com.paulok777.model.dao.DaoFactory;
import com.paulok777.model.dao.UserDao;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.entity.User;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongPasswordException;
import com.paulok777.model.exception.cash_register_exc.login_exc.WrongUsernameException;
import com.paulok777.model.exception.cash_register_exc.registration_exc.DuplicateUsernameException;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.doThrow;

public class UserServiceTest {
    DaoFactory daoFactory = mock(DaoFactory.class);
    PasswordEncoder passwordEncoder = new MD5PasswordEncoder();
    UserDao userDao = mock(UserDao.class);
    UserService userService = new UserService(daoFactory, passwordEncoder);

    private final UserDTO userDTO = UserDTO.builder()
            .firstName("Pavlo")
            .lastName("Trotsiuk")
            .username("PauL")
            .password("1234")
            .email("mail@mail.com")
            .phoneNumber("888888888")
            .role("CASHIER")
            .build();

    private final User user = User.builder()
            .firstName(userDTO.getFirstName())
            .lastName(userDTO.getLastName())
            .username(userDTO.getUsername())
            .password(passwordEncoder.encode("1234"))
            .email(userDTO.getEmail())
            .phoneNumber(userDTO.getPhoneNumber())
            .role(User.Role.valueOf(userDTO.getRole()))
            .build();

    @Test
    public void testSaveNewUserShouldWorkWithoutExceptionsWhenDaoDontGenerateExceptions() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        userService.saveNewUser(userDTO);
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).create(user);
    }

    @Test(expected = DuplicateUsernameException.class)
    public void testSaveNewUserShouldThrowDuplicateUsernameExceptionWhenDaoGenerateException() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        doThrow(new RuntimeException()).when(userDao).create(user);
        userService.saveNewUser(userDTO);
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).create(user);
    }

    @Test
    public void testGetUserByUsernameShouldReturnUserWhenUserWithThisUsernameExists() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        when(userDao.findByUsername("PauL")).thenReturn(Optional.of(user));
        User userFromMethod = userService.getUserByUsername("PauL");
        assertEquals(user, userFromMethod);
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).findByUsername("PauL");
    }

    @Test(expected = RuntimeException.class)
    public void testGetUserByUsernameShouldThrowRuntimeExceptionWhenUsernameWithThisUsernameAbsent() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        when(userDao.findByUsername("absent")).thenReturn(Optional.empty());
        userService.getUserByUsername("absent");
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).findByUsername("absent");
    }

    @Test
    public void testCheckUserAndGetRoleShouldReturnUserRoleWhenUserWithThisUsernameAndPassExists() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        when(userDao.findByUsername("PauL")).thenReturn(Optional.of(user));
        userService.checkUserAndGetRole("PauL", "1234");
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).findByUsername("PauL");
    }

    @Test(expected = WrongUsernameException.class)
    public void testCheckUserAndGetRoleShouldThrowWrongUsernameExceptionWhenUserWithThisUsernameAbsent() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        when(userDao.findByUsername("absent")).thenReturn(Optional.empty());
        userService.checkUserAndGetRole("absent", "1234");
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).findByUsername("absent");
    }

    @Test(expected = WrongPasswordException.class)
    public void testCheckUserAndGetRoleShouldThrowWrongPasswordExceptionWhenUserWithThisPassAbsent() {
        when(daoFactory.createUserDao()).thenReturn(userDao);
        when(userDao.findByUsername("PauL")).thenReturn(Optional.of(user));
        userService.checkUserAndGetRole("PauL", "12345");
        verify(daoFactory, times(1)).createUserDao();
        verify(userDao, times(1)).findByUsername("PauL");
    }
}
