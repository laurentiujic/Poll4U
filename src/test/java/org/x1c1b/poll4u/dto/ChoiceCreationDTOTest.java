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

public class ChoiceCreationDTOTest {

    private Validator validator;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateWithBlankFields() {

        ChoiceCreationDTO creation = new ChoiceCreationDTO();
        Set<ConstraintViolation<ChoiceCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }

    @Test
    public void validateWithValidFields() {

        ChoiceCreationDTO creation = new ChoiceCreationDTO();
        creation.setDescription("Green");

        Set<ConstraintViolation<ChoiceCreationDTO>> violations = validator.validate(creation);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithToLongDescription() {

        ChoiceCreationDTO creation = new ChoiceCreationDTO();
        creation.setDescription(RandomStringUtils.random(51));

        Set<ConstraintViolation<ChoiceCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }
}