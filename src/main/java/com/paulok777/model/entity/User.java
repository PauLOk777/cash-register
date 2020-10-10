package com.paulok777.model.entity;

import com.paulok777.model.dto.UserDTO;

public class User {
    private Long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Role role;

    public enum Role {
        CASHIER,
        SENIOR_CASHIER,
        COMMODITY_EXPERT,
        UNKNOWN;

        public String getAuthority() {
            return this.name();
        }
    }

    public User() {
    }

    public User(UserDTO userDTO) {
        this.username = userDTO.getUsername();
        this.password = userDTO.getPassword();
        this.firstName = userDTO.getFirstName();
        this.lastName = userDTO.getLastName();
        this.email = userDTO.getEmail();
        this.phoneNumber = userDTO.getPhoneNumber();
        this.role = Role.valueOf(userDTO.getRole());
    }

    public User(Long id, String username, String password, String firstName, String lastName, String email, String phoneNumber, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public static User.UserBuilder builder() {
        return new User.UserBuilder();
    }

    public static class UserBuilder {
        private Long id;
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private Role role;

        UserBuilder() {
        }

        public User.UserBuilder id(final Long id) {
            this.id = id;
            return this;
        }

        public User.UserBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public User.UserBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public User.UserBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public User.UserBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public User.UserBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public User.UserBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public User.UserBuilder role(final User.Role role) {
            this.role = role;
            return this;
        }

        public User build() {
            return new User(id, username, password, firstName, lastName, email, phoneNumber, role);
        }
    }
}
