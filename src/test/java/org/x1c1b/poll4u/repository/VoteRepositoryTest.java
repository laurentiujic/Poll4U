package org.x1c1b.poll4u.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.x1c1b.poll4u.model.ChoiceState;

import java.util.List;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/setup-users.sql", "/sql/setup-polls.sql", "/sql/setup-votes.sql"})
public class VoteRepositoryTest {

    @Autowired private VoteRepository voteRepository;

    @Test public void existsByPollIdAndUserId() {

        assertTrue(voteRepository.existsByPollIdAndUserId(1L, 1L));
    }

    @Test public void doesntExistByPollIdAndUserId() {

        assertFalse(voteRepository.existsByPollIdAndUserId(1L, 2L));
    }

    @Test public void countByPollId() {

        assertEquals(1, voteRepository.countByPollId(1L));
    }

    @Test public void countByChoiceId() {

        assertEquals(0, voteRepository.countByChoiceId(1L));
    }

    @Test public void countByPollIdGroupByChoiceId() {

        List<ChoiceState> list = voteRepository.countByPollIdGroupByChoiceId(1L);

        assertEquals(1, list.size());
        assertEquals(2L, (long) list.get(0).getChoiceId());
        assertEquals(1L, (long) list.get(0).getVoteCount());
    }
}