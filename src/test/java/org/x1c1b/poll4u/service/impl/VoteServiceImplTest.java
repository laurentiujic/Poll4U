package org.x1c1b.poll4u.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.x1c1b.poll4u.dto.VoteCreationDTO;
import org.x1c1b.poll4u.error.AlreadyVotedException;
import org.x1c1b.poll4u.error.PollExpiredException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.Choice;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.VoteService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class VoteServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private PollRepository pollRepository;
    @Mock private VoteRepository voteRepository;

    private VoteService service;

    private Poll expectedPoll;

    @Before
    public void setUp() throws Exception {

        service = new VoteServiceImpl(voteRepository, pollRepository, userRepository);

        Choice firstChoice = new Choice();
        firstChoice.setId(1L);
        firstChoice.setDescription("Green");

        Choice secondChoice = new Choice();
        secondChoice.setId(1L);
        secondChoice.setDescription("Green");

        expectedPoll = new Poll();
        expectedPoll.setId(1L);
        expectedPoll.setQuestion("What's your favorite color?");
        expectedPoll.setCreatedAt(new Date());
        expectedPoll.setCreatedAt(new Date());
        expectedPoll.setUpdatedBy(1L);
        expectedPoll.setCreatedBy(1L);
        expectedPoll.setChoices(Arrays.asList(firstChoice, secondChoice));
        expectedPoll.setExpiration(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parse("2020-07-04T12:08:56.235-0700"));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void voteForNonExistingPoll() {

        when(pollRepository.findById(any())).thenReturn(Optional.empty());

        VoteCreationDTO creation = new VoteCreationDTO(1L);

        service.voteForPoll(1L, 1L, creation);
    }

    @Test(expected = PollExpiredException.class)
    public void voteForExpiredPoll() throws Exception {

        VoteCreationDTO creation = new VoteCreationDTO(1L);
        expectedPoll.setExpiration(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
                .parse("2001-07-04T12:08:56.235-0700"));

        when(pollRepository.findById(any())).thenReturn(Optional.of(expectedPoll));

        service.voteForPoll(1L, 1L, creation);
    }

    @Test(expected = AlreadyVotedException.class)
    public void voteForAlreadyVotedPoll() {

        when(pollRepository.findById(any())).thenReturn(Optional.of(expectedPoll));
        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(voteRepository.existsByPollIdAndUserId(any(), any())).thenReturn(true);

        VoteCreationDTO creation = new VoteCreationDTO(1L);

        service.voteForPoll(1L, 1L, creation);
    }
}