package org.x1c1b.poll4u.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    private Validator validator;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test public void validateBlank() {

        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(3, violations.size());
    }

    @Test public void validateValidUser() {

        User user = new User();
        user.setUsername("user");
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test public void validateInvalidEmail() {

        User user = new User();
        user.setUsername("user");
        user.setEmail("invalid");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test public void validateShortUsername() {

        User user = new User();
        user.setUsername(RandomStringUtils.random(2));
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test public void validateLongUsername() {

        User user = new User();
        user.setUsername(RandomStringUtils.random(20));
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }
}