package org.x1c1b.poll4u.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

public class UserUpdateDTOTest {

    private Validator validator;
    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        modelMapper = new ModelMapper();
    }

    @Test
    public void validateBlank() {

        UserUpdateDTO update = new UserUpdateDTO();
        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(4, violations.size());
    }

    @Test public void validateValidUserUpdateDTO() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setId(1L);
        update.setUsername("user");
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(0, violations.size());
    }

    @Test public void validateInvalidEmail() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setId(1L);
        update.setUsername("user");
        update.setEmail("invalid");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }

    @Test public void validateShortUsername() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setId(1L);
        update.setUsername(RandomStringUtils.random(2));
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }

    @Test public void validateLongUsername() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setId(1L);
        update.setUsername(RandomStringUtils.random(20));
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }

    @Test public void validateInvalidPassword() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setId(1L);
        update.setUsername("user");
        update.setEmail("user@web.de");
        update.setPassword("abcdefg");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }

    @Test public void validateEmptyId() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setUsername("user");
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        Set<ConstraintViolation<UserUpdateDTO>> violations = validator.validate(update);

        assertEquals(1, violations.size());
    }
}