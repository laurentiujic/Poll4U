package org.x1c1b.poll4u.dto;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import static org.junit.Assert.*;

public class PollCreationDTOTest {

    private Validator validator;

    @Before
    public void setUp() {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateWithBlankFields() {

        PollCreationDTO creation = new PollCreationDTO();
        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(3, violations.size());
    }

    @Test public void validateWithValidFields() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion("What's your favorite color?");
        creation.setExpiration(new Date());
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"),
                new ChoiceCreationDTO("Red")));

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(0, violations.size());
    }

    @Test public void validateWithoutChoices() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion("What's your favorite color?");
        creation.setExpiration(new Date());

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithInvalidChoice() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion("What's your favorite color?");
        creation.setExpiration(new Date());
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"), new ChoiceCreationDTO()));

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithoutExpiration() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion("What's your favorite color?");
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"),
                new ChoiceCreationDTO("Red")));

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithToLongQuestion() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setQuestion(RandomStringUtils.random(141));
        creation.setExpiration(new Date());
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"),
                new ChoiceCreationDTO("Red")));

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }

    @Test public void validateWithoutQuestion() {

        PollCreationDTO creation = new PollCreationDTO();
        creation.setExpiration(new Date());
        creation.setChoices(Arrays.asList(new ChoiceCreationDTO("Green"),
                new ChoiceCreationDTO("Red")));

        Set<ConstraintViolation<PollCreationDTO>> violations = validator.validate(creation);

        assertEquals(1, violations.size());
    }
}