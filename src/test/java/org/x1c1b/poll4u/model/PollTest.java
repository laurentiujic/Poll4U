package org.x1c1b.poll4u.model;

import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.x1c1b.poll4u.dto.PollDTO;

import java.util.Date;

import static org.junit.Assert.*;

public class PollTest {

    private ModelMapper modelMapper;

    @Before
    public void setUp() {

        modelMapper = new ModelMapper();
    }

    @Test
    public void mapToDTO() {

        Choice firstCoice = new Choice("Green");
        firstCoice.setId(1L);

        Choice secondChoice = new Choice("Red");
        secondChoice.setId(2L);

        Poll poll = new Poll("What's your favorite color?");
        poll.setId(1L);
        poll.addChoice(firstCoice);
        poll.addChoice(secondChoice);
        poll.setExpiration(new Date());
        poll.setUpdatedBy(1L);
        poll.setCreatedBy(1L);
        poll.setUpdatedAt(new Date());
        poll.setCreatedAt(new Date());

        PollDTO dto = modelMapper.map(poll, PollDTO.class);

        assertEquals(poll.getId(), dto.getId());
        assertEquals(poll.getQuestion(), dto.getQuestion());
        assertEquals(poll.getExpiration(), dto.getExpiration());
        assertEquals(poll.getChoices().size(), dto.getChoices().size());
        assertEquals(poll.getUpdatedAt(), dto.getUpdatedAt());
        assertEquals(poll.getCreatedAt(), dto.getCreatedAt());
    }
}