package org.x1c1b.poll4u.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.ChoiceState;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.PollService;

import javax.transaction.Transactional;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PollServiceImpl implements PollService {

    private PollRepository pollRepository;
    private VoteRepository voteRepository;
    private ModelMapper modelMapper;

    @Autowired
    public PollServiceImpl(PollRepository pollRepository,
                           VoteRepository voteRepository,
                           ModelMapper modelMapper) {

        this.pollRepository = pollRepository;
        this.modelMapper = modelMapper;
        this.voteRepository = voteRepository;
    }

    @Override
    public Page<PollDTO> findAll(Pageable pageable) {

        Page<PollDTO> page = pollRepository.findAll(pageable)
                .map(poll -> modelMapper.map(poll, PollDTO.class));

        page.forEach(pollDTO -> {

            Map<Long, Long> state = voteRepository.countByPollIdGroupByChoiceId(pollDTO.getId()).stream()
                    .collect(Collectors.toMap(ChoiceState::getChoiceId, ChoiceState::getVoteCount));

            pollDTO.getChoices().forEach(choiceDTO -> {

                choiceDTO.setVoteCount(state.getOrDefault(choiceDTO.getId(), 0L));
            });
        });

        return page;
    }

    @Override
    public Page<PollDTO> findByCreatedBy(Long userId, Pageable pageable) {

        Page<PollDTO> page = pollRepository.findByCreatedBy(userId, pageable)
                .map(poll -> modelMapper.map(poll, PollDTO.class));

        page.forEach(pollDTO -> {

            Map<Long, Long> state = voteRepository.countByPollIdGroupByChoiceId(pollDTO.getId()).stream()
                    .collect(Collectors.toMap(ChoiceState::getChoiceId, ChoiceState::getVoteCount));

            pollDTO.getChoices().forEach(choiceDTO -> {

                choiceDTO.setVoteCount(state.getOrDefault(choiceDTO.getId(), 0L));
            });
        });

        return page;
    }

    @Override
    public PollDTO findById(Long id) {

        PollDTO pollDTO = modelMapper.map(pollRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No poll with such identifier found")),
                PollDTO.class);

        Map<Long, Long> state = voteRepository.countByPollIdGroupByChoiceId(id).stream()
                .collect(Collectors.toMap(ChoiceState::getChoiceId, ChoiceState::getVoteCount));

        pollDTO.getChoices().forEach(choiceDTO -> {

            choiceDTO.setVoteCount(state.getOrDefault(choiceDTO.getId(), 0L));
        });

        return pollDTO;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_USER')")
    public PollDTO create(PollCreationDTO creation) {

        Poll poll = modelMapper.map(creation, Poll.class);
        poll.getChoices().forEach(choice -> choice.setPoll(poll));

        return modelMapper.map(pollRepository.save(poll), PollDTO.class);
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
