package org.x1c1b.poll4u.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.x1c1b.poll4u.dto.ProfileDTO;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO mapUser(User user);
    ProfileDTO mapProfile(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User map(RegistrationDTO registration);
}
