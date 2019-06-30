package org.x1c1b.poll4u.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.x1c1b.poll4u.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class RegistrationDTOTest {

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

        RegistrationDTO registration = new RegistrationDTO();
        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(3, violations.size());
    }

    @Test public void validateValidRegistrationDTO() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(0, violations.size());
    }

    @Test public void validateInvalidEmail() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("invalid");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateShortUsername() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername(RandomStringUtils.random(2));
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateLongUsername() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername(RandomStringUtils.random(20));
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateInvalidPassword() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("user@web.de");
        registration.setPassword("abcdefg");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void mapToEntity() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        User user = modelMapper.map(registration, User.class);

        assertEquals(registration.getUsername(), user.getUsername());
        assertEquals(registration.getEmail(), user.getEmail());
        assertEquals(registration.getPassword(), user.getPassword());
    }
}