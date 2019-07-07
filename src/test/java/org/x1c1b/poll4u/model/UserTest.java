package org.x1c1b.poll4u.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.x1c1b.poll4u.dto.UserDTO;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class UserTest {

    private Validator validator;
    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        modelMapper = new ModelMapper();
    }

    @Test public void validateWithBlankFields() {

        User user = new User();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(3, violations.size());
    }

    @Test public void validateWithValidFields() {

        User user = new User();
        user.setUsername("user");
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithMalformedEmail() {

        User user = new User();
        user.setUsername("user");
        user.setEmail("invalid");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToShortUsername() {

        User user = new User();
        user.setUsername(RandomStringUtils.random(2));
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToLongUsername() {

        User user = new User();
        user.setUsername(RandomStringUtils.random(20));
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertEquals(1, violations.size());
    }

    @Test public void mapToDTO() {

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setEmail("user@web.de");
        user.setPassword("Abc123");

        UserDTO dto = modelMapper.map(user, UserDTO.class);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getUsername(), dto.getUsername());
        assertEquals(user.getEmail(), dto.getEmail());
    }
}