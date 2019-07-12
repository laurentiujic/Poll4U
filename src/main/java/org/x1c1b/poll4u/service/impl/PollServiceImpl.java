package org.x1c1b.poll4u.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.dto.mapper.PollMapper;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.PollService;

import javax.transaction.Transactional;

@Service
public class PollServiceImpl implements PollService {

    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private PollMapper pollMapper;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           VoteRepository voteRepository,
                           PollMapper pollMapper) {

        this.pollRepository = pollRepository;
        this.pollMapper = pollMapper;
        this.voteRepository = voteRepository;
    }

    @Override
    public Page<PollDTO> findAll(Pageable pageable) {

        return pollRepository.findAll(pageable)
                .map(poll -> pollMapper.map(poll));
    }

    @Override
    public Page<PollDTO> findByCreatedBy(Long userId, Pageable pageable) {

        return pollRepository.findByCreatedBy(userId, pageable)
                .map(poll -> pollMapper.map(poll));
    }

    @Override
    public PollDTO findById(Long id) {

        return pollMapper.map(pollRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No poll with such identifier found")));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public PollDTO create(PollCreationDTO creation) {

        Poll poll = pollMapper.map(creation);
        poll.getChoices().forEach(choice -> choice.setPoll(poll));

        return pollMapper.map(pollRepository.save(poll));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or @authorization.isPollCreator(authentication, #id)")
    @Transactional
    public void deleteById(Long id) {

        if(!pollRepository.existsById(id)) throw new ResourceNotFoundException("No poll with such identifier found");
        voteRepository.deleteByPollId(id);
        pollRepository.deleteById(id);
    }
}
