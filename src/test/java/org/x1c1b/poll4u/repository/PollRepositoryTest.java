package org.x1c1b.poll4u.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.x1c1b.poll4u.model.Choice;
import org.x1c1b.poll4u.model.Poll;

import javax.validation.ConstraintViolationException;
import java.util.Date;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class PollRepositoryTest {

    @Autowired private PollRepository pollRepository;

    @Test
    public void savePoll() {

        Poll poll = new Poll("What's your favorite color?");
        poll.setExpiration(new Date());
        poll.addChoice(new Choice("Green"));
        poll.addChoice(new Choice("Red"));

        Poll expected = pollRepository.save(poll);

        assertEquals(expected.getQuestion(), poll.getQuestion());
        assertEquals(expected.getExpiration(), poll.getExpiration());
    }

    @Test(expected = ConstraintViolationException.class)
    public void savePollWithoutChoices() {

        Poll poll = new Poll("What's your favorite color?");
        poll.setExpiration(new Date());

        pollRepository.save(poll);
    }

    @Test(expected = ConstraintViolationException.class)
    public void savePollWithoutExpirationDate() {

        Poll poll = new Poll("What's your favorite color?");
        poll.addChoice(new Choice("Green"));
        poll.addChoice(new Choice("Red"));

        pollRepository.save(poll);
    }

    @Test public void findByCreatedBy() {

        Poll firstPoll = new Poll("What's your favorite color?");
        firstPoll.setExpiration(new Date());
        firstPoll.addChoice(new Choice("Green"));
        firstPoll.addChoice(new Choice("Red"));
        firstPoll.setCreatedBy(1L);

        Poll secondPoll = new Poll("What's your favorite hobby?");
        secondPoll.setExpiration(new Date());
        secondPoll.addChoice(new Choice("Football"));
        secondPoll.addChoice(new Choice("Tennis"));
        secondPoll.setCreatedBy(2L);

        pollRepository.save(firstPoll);
        pollRepository.save(secondPoll);

        Page<Poll> page = pollRepository.findByCreatedBy(2L, Pageable.unpaged());

        assertEquals(1, page.getTotalElements());
        assertEquals(secondPoll.getQuestion(), page.getContent().get(0).getQuestion());
        assertEquals(secondPoll.getCreatedBy(), page.getContent().get(0).getCreatedBy());
    }
}