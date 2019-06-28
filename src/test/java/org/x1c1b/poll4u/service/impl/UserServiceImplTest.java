package org.x1c1b.poll4u.service.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.x1c1b.poll4u.error.BadRequestException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.RoleRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.service.UserService;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private PasswordEncoder passwordEncoder;

    private UserService service;

    @Before
    public void setUp() {

        service = new UserServiceImpl(userRepository, roleRepository, passwordEncoder);
    }

    @Test(expected = BadRequestException.class)
    public void createUserWithExistingEmail() {

        User user = new User();
        user.setUsername("user");
        user.setUsername("user@web.de");
        user.setPassword("Abc123");

        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        service.create(user);
    }

    @Test(expected = BadRequestException.class)
    public void createUserWithExistingUsername() {

        User user = new User();
        user.setUsername("user");
        user.setUsername("user@web.de");
        user.setPassword("Abc123");

        when(userRepository.findByUsername(any())).thenReturn(Optional.of(user));

        service.create(user);
    }

    @Test(expected = BadRequestException.class)
    public void updateWithExistingEmail() {

        User user = new User();
        user.setUsername("user");
        user.setUsername("user@web.de");
        user.setPassword("Abc123");

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));

        service.updateById(1L, user);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void updateNonExisting() {

        User user = new User();
        user.setUsername("user");
        user.setUsername("user@web.de");
        user.setPassword("Abc123");

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        service.updateById(1L, user);
    }
}