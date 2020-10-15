package com.paulok777.model.dto;

public class UserDTO {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String role;

    public static UserDTO.UserDTOBuilder builder() {
        return new UserDTO.UserDTOBuilder();
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getRole() {
        return this.role;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setRole(final String role) {
        this.role = role;
    }

    public String toString() {
        return "UserDTO(username=" + this.getUsername() + ", password=" + this.getPassword() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ", phoneNumber=" + this.getPhoneNumber() + ", role=" + this.getRole() + ")";
    }

    public UserDTO(final String username, final String password, final String firstName, final String lastName, final String email, final String phoneNumber, final String role) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public UserDTO() {
    }

    public static class UserDTOBuilder {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String phoneNumber;
        private String role;

        UserDTOBuilder() {
        }

        public UserDTO.UserDTOBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public UserDTO.UserDTOBuilder password(final String password) {
            this.password = password;
            return this;
        }

        public UserDTO.UserDTOBuilder firstName(final String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserDTO.UserDTOBuilder lastName(final String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserDTO.UserDTOBuilder email(final String email) {
            this.email = email;
            return this;
        }

        public UserDTO.UserDTOBuilder phoneNumber(final String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserDTO.UserDTOBuilder role(final String role) {
            this.role = role;
            return this;
        }

        public UserDTO build() {
            return new UserDTO(this.username, this.password, this.firstName, this.lastName, this.email, this.phoneNumber, this.role);
        }

        public String toString() {
            return "UserDTO.UserDTOBuilder(username=" + this.username + ", password=" + this.password + ", firstName=" + this.firstName + ", lastName=" + this.lastName + ", email=" + this.email + ", phoneNumber=" + this.phoneNumber + ", role=" + this.role + ")";
        }
    }
}
