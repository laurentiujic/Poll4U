package org.x1c1b.poll4u.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
import org.x1c1b.poll4u.dto.mapper.UserMapper;
import org.x1c1b.poll4u.error.BadRequestException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.RoleRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.PollService;
import org.x1c1b.poll4u.service.UserService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private VoteRepository voteRepository;
    @Mock private PollService pollService;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserMapper userMapper;

    private UserService service;

    @Before
    public void setUp() {

        service = new UserServiceImpl(userRepository, roleRepository, voteRepository, pollService, passwordEncoder, userMapper);
    }

    @Test(expected = BadRequestException.class)
    public void createUserWithExistingEmail() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setUsername("user@web.de");
        registration.setPassword("Abc123");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        service.create(registration);
    }

    @Test(expected = BadRequestException.class)
    public void createUserWithExistingUsername() {

        RegistrationDTO registration = new RegistrationDTO();
        registration.setUsername("user");
        registration.setUsername("user@web.de");
        registration.setPassword("Abc123");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(new User()));

        service.create(registration);
    }

    @Test(expected = BadRequestException.class)
    public void updateWithExistingEmail() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        when(userRepository.findById(any())).thenReturn(Optional.of(new User()));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(new User()));

        service.updateById(1L, update);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNonExisting() {

        UserUpdateDTO update = new UserUpdateDTO();
        update.setEmail("user@web.de");
        update.setPassword("Abc123");

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        service.updateById(1L, update);
    }
}