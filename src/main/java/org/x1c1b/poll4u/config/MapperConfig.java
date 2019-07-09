package org.x1c1b.poll4u.config;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.x1c1b.poll4u.dto.ChoiceDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.model.Choice;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.security.UserPrincipal;

import javax.persistence.Convert;
import java.util.Optional;

@Configuration
public class MapperConfig {

    @Autowired private UserRepository userRepository;
    @Autowired private VoteRepository voteRepository;

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper mapper = new ModelMapper();
        mapper.addConverter(toUserDTO());
        mapper.addConverter(toChoiceDTO());

        return mapper;
    }

    private Converter<Long, UserDTO> toUserDTO() {

        return new AbstractConverter<Long, UserDTO>() {

            @Override protected UserDTO convert(Long id) {

                User user = userRepository.findById(id).orElse(null);

                if(null != user) {

                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setUsername(user.getUsername());
                    dto.setEmail(user.getEmail());

                    return dto;
                }

                return null;
            }
        };
    }

    private Converter<Choice, ChoiceDTO> toChoiceDTO() {

        return new AbstractConverter<Choice, ChoiceDTO>() {

            @Override protected ChoiceDTO convert(Choice choice) {

                long votes = voteRepository.countByChoiceId(choice.getId());

                ChoiceDTO dto = new ChoiceDTO();
                dto.setId(choice.getId());
                dto.setDescription(choice.getDescription());
                dto.setVotes(votes);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if(null == authentication ||
                        !authentication.isAuthenticated() ||
                        authentication instanceof AnonymousAuthenticationToken) {

                    dto.setVoted(false);

                } else {

                    UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
                    dto.setVoted(voteRepository.existsByChoiceIdAndUserId(
                            choice.getId(), principal.getId()));
                }

                return dto;
            }
        };
    }
}
