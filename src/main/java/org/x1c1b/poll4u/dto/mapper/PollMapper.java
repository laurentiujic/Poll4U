package org.x1c1b.poll4u.dto.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.repository.UserRepository;

@Mapper(componentModel = "spring", uses = {ChoiceMapper.class, UserMapper.class})
@SuppressWarnings("WeakerAccess")
public abstract class PollMapper {

    @Autowired protected UserRepository userRepository;
    @Autowired protected UserMapper userMapper;

    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract PollDTO map(Poll poll);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    public abstract Poll map(PollCreationDTO creation);

    @AfterMapping
    protected void postMap(Poll poll, @MappingTarget PollDTO dto) {

        dto.setCreatedBy(userMapper.map(userRepository
                .findById(poll.getCreatedBy()).orElse(null)));

        dto.setUpdatedBy(userMapper.map(userRepository
                .findById(poll.getUpdatedBy()).orElse(null)));
    }
}
