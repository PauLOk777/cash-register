package com.paulok777.controller.util;

import com.paulok777.model.dto.ProductDTO;
import com.paulok777.model.dto.UserDTO;
import com.paulok777.model.exception.cash_register_exc.FieldValidationException;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {
    @Test
    public void testValidateUserShouldNotThrowExceptionsWhenAllFieldsMatchRegex() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
        assertTrue(true);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenFirstNameIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("P")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenLastNameIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("T")
                .username("PauL")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenEmailIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("1234")
                .email("mail@mail")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenUsernameIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("Pau")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenPasswordIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("123")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenPhoneNumberIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888")
                .role("CASHIER")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateUserShouldThrowFieldValidationExceptionWhenRoleIsInvalid() {
        UserDTO userDTO = UserDTO.builder()
                .firstName("Pavlo")
                .lastName("Trotsiuk")
                .username("PauL")
                .password("1234")
                .email("mail@mail.com")
                .phoneNumber("888888888")
                .role("CASH")
                .build();
        Validator.validateUser(userDTO);
    }

    @Test
    public void testValidateProductShouldNotThrowExceptionsWhenAllFieldsMatchRegex() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("1234")
                .name("Banana")
                .price(100)
                .measure("BY_WEIGHT")
                .amount(4000L)
                .build();
        Validator.validateProduct(productDTO);
        assertTrue(true);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateProductShouldThrowFieldValidationExceptionWhenCodeIsInvalid() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("123")
                .name("Banana")
                .price(100)
                .measure("BY_WEIGHT")
                .amount(4000L)
                .build();
        Validator.validateProduct(productDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateProductShouldThrowFieldValidationExceptionWhenNameIsInvalid() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("1234")
                .name(" ")
                .price(100)
                .measure("BY_WEIGHT")
                .amount(4000L)
                .build();
        Validator.validateProduct(productDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateProductShouldThrowFieldValidationExceptionWhenPriceIsInvalid() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("1234")
                .name("Banana")
                .price(0)
                .measure("BY_WEIGHT")
                .amount(4000L)
                .build();
        Validator.validateProduct(productDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateProductShouldThrowFieldValidationExceptionWhenAmountForCommodityExpertIsInvalid() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("1234")
                .name("Banana")
                .price(100)
                .measure("BY_WEIGHT")
                .amount(-1L)
                .build();
        Validator.validateProduct(productDTO);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateProductShouldThrowFieldValidationExceptionWhenMeasureIsInvalid() {
        ProductDTO productDTO = ProductDTO.builder()
                .code("1234")
                .name("Banana")
                .price(100)
                .measure("BY")
                .amount(4000L)
                .build();
        Validator.validateProduct(productDTO);
    }

    @Test
    public void testValidateAmountForCashierShouldNotThrowExceptionsWhenAmountMoreThanOne() {
        Validator.validateAmountForCashier(3L);
        assertTrue(true);
    }

    @Test(expected = FieldValidationException.class)
    public void testValidateAmountForCashierShouldThrowFieldValidationExceptionWhenAmountLessThanOne() {
        Validator.validateAmountForCashier(0L);
    }
}
