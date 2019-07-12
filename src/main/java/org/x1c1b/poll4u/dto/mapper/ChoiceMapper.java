package org.x1c1b.poll4u.dto.mapper;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.x1c1b.poll4u.dto.ChoiceCreationDTO;
import org.x1c1b.poll4u.dto.ChoiceDTO;
import org.x1c1b.poll4u.model.Choice;
import org.x1c1b.poll4u.repository.VoteRepository;

@Mapper(componentModel = "spring")
@SuppressWarnings("WeakerAccess")
public abstract class ChoiceMapper {

    @Autowired protected VoteRepository voteRepository;

    @Mapping(source = "id", target = "id")
    @Mapping(target = "votes", ignore = true)
    public abstract ChoiceDTO map(Choice choice);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "poll", ignore = true)
    public abstract Choice map(ChoiceCreationDTO creation);

    @AfterMapping
    protected void postMap(Choice choice, @MappingTarget ChoiceDTO dto) {

        dto.setVotes(voteRepository.countByChoiceId(choice.getId()));
    }
}
