package org.x1c1b.poll4u.model;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.x1c1b.poll4u.dto.ChoiceDTO;

import static org.junit.Assert.*;

public class ChoiceTest {

    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        modelMapper = new ModelMapper();
    }

    @Test
    public void mapToDTO() {

        Choice choice = new Choice("My favorite color is green");
        choice.setId(1L);

        ChoiceDTO dto = modelMapper.map(choice, ChoiceDTO.class);

        assertEquals(choice.getId(), dto.getId());
        assertEquals(choice.getDescription(), dto.getDescription());
    }
}