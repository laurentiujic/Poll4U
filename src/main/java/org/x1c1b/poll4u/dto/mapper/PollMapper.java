package org.x1c1b.poll4u.dto.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.security.UserPrincipal;

@Mapper(componentModel = "spring", uses = {
        ChoiceMapper.class,
        UserMapper.class})
@SuppressWarnings("WeakerAccess")
public abstract class PollMapper {

    @Autowired protected UserRepository userRepository;
    @Autowired protected VoteRepository voteRepository;
    @Autowired protected UserMapper userMapper;

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "voted", ignore = true)
    @Mapping(target = "votes", ignore = true)
    public abstract PollDTO map(Poll poll);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract Poll map(PollCreationDTO creation);

    @AfterMapping
    protected void postMap(Poll poll, @MappingTarget PollDTO dto) {

        dto.setCreatedBy(userMapper.mapProfile(userRepository
                .findById(poll.getCreatedBy()).orElse(null)));

        dto.setUpdatedBy(userMapper.mapProfile(userRepository
                .findById(poll.getUpdatedBy()).orElse(null)));

        dto.setVotes(voteRepository.countByPollId(poll.getId()));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(null == authentication ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {

            dto.setVoted(false);

        } else {

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
            dto.setVoted(voteRepository.existsByPollIdAndUserId(poll.getId(), principal.getId()));
        }
    }
}
