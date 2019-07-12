package org.x1c1b.poll4u.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;
import org.x1c1b.poll4u.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Set;

import static org.junit.Assert.*;

public class RegistrationDTOTest {

    private Validator validator;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateWithBlankFields() {

        RegistrationDTO registration = new RegistrationDTO();
        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(3, violations.size());
    }

    @Test public void validateWithValidFields() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithMalformedEmail() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("invalid");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithoutEmail() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToLongEmail() {

        /*
        The maximum allowed local part size is 64 characters
        http://www.rfc-editor.org/errata_search.php?rfc=3696&eid=1690
         */

        String local = RandomStringUtils.random(64, true, true);
        String domain = RandomStringUtils.random(40, true, true);

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail(local + "@" + domain + ".com");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToShortUsername() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername(RandomStringUtils.random(2));
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToLongUsername() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername(RandomStringUtils.random(20));
        registration.setEmail("user@web.de");
        registration.setPassword("Abc123");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithMalformedPassword() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setEmail("user@web.de");
        registration.setPassword("abcdefg");

        Set<ConstraintViolation<RegistrationDTO>> violations = validator.validate(registration);

        assertEquals(1, violations.size());
    }
}