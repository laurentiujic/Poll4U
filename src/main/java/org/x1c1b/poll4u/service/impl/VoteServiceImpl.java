package org.x1c1b.poll4u.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.dto.VoteCreationDTO;
import org.x1c1b.poll4u.error.AlreadyVotedException;
import org.x1c1b.poll4u.error.PollExpiredException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.Choice;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.model.Vote;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.VoteService;

import java.util.Date;

@Service
public class VoteServiceImpl implements VoteService {

    private VoteRepository voteRepository;
    private PollRepository pollRepository;
    private UserRepository userRepository;

    @Autowired
    public VoteServiceImpl(VoteRepository voteRepository,
                           PollRepository pollRepository,
                           UserRepository userRepository) {

        this.voteRepository = voteRepository;
        this.pollRepository = pollRepository;
        this.userRepository = userRepository;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public void voteForPoll(Long userId, Long pollId, VoteCreationDTO creation) {

        Poll poll = pollRepository.findById(pollId).orElseThrow(
                () -> new ResourceNotFoundException("No poll with such identifier found"));

        if(poll.getExpiration().before(new Date())) {

            throw new PollExpiredException("This poll has already expired");
        }

        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found"));

        if(voteRepository.existsByPollIdAndUserId(pollId, userId)) {

            throw new AlreadyVotedException("Already voted for this poll");
        }

        Choice selectedChoice = poll.getChoices().stream()
                .filter(choice -> choice.getId().equals(creation.getChoiceId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("No choice with such identifier found"));

        Vote vote = new Vote();
        vote.setPoll(poll);
        vote.setUser(user);
        vote.setChoice(selectedChoice);

        voteRepository.save(vote);
    }
}
