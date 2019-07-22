package org.x1c1b.poll4u.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

public class UserUpdateDTOTest {

    private Validator validator;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateWithBlankField() {

        UserUpdateDTO update = new UserUpdateDTO();
        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithValidFields() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithoutOptionalPassword() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("user@web.de");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithoutOptionalEmail() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithMalformedEmail() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("invalid");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithMalformedPassword() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("user@web.de");
        update.setPassword("abcdefg");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }
}