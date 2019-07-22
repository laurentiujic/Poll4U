package org.x1c1b.poll4u.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.dto.ProfileDTO;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
import org.x1c1b.poll4u.dto.mapper.UserMapper;
import org.x1c1b.poll4u.error.BadRequestException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.Role;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.repository.RoleRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.repository.VoteRepository;
import org.x1c1b.poll4u.service.PollService;
import org.x1c1b.poll4u.service.UserService;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private VoteRepository voteRepository;
    private RoleRepository roleRepository;

    private PollService pollService;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           VoteRepository voteRepository,
                           PollService pollService,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.voteRepository = voteRepository;
        this.pollService = pollService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public List<ProfileDTO> findAll() {

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> userMapper.mapProfile(user))
                .collect(Collectors.toList());
    }

    @Override
    public Page<ProfileDTO> findAll(Pageable pageable) {

        return userRepository.findAll(pageable).map((user) -> userMapper.mapProfile(user));
    }

    @Override
    public ProfileDTO findById(Long id) {

        return userMapper.mapProfile(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found")));
    }

    @Override
    public ProfileDTO findByUsername(String username) {

        return userMapper.mapProfile(userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("No user with such username found")));
    }

    @Override
    public ProfileDTO findByEmail(String email) {

        return userMapper.mapProfile(userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("No user with such email found")));
    }

    @Override
    public UserDTO create(RegistrationDTO registration) {

        boolean isNamePresent = userRepository.findByUsername(registration.getUsername()).isPresent();
        boolean isEmailPresent = userRepository.findByEmail(registration.getEmail()).isPresent();

        if(isNamePresent) throw new BadRequestException("Username is already present");
        if(isEmailPresent) throw new BadRequestException("Email is already in use");

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("No such role found"));

        User user = userMapper.map(registration);

        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Collections.singleton(role));

        return userMapper.mapUser(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or @authorization.isAccountOwner(authentication, #id)")
    public UserDTO updateById(Long id, UserUpdateDTO update) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found"));

        /*
        "Be strict in what you produces and be tolerant in what you consumes", ignore additional
        fields of entity. Just update partially the updatable fields.
         */

        if(update.getEmail().isPresent()) { // Update email if present

            boolean isEmailPresent = userRepository.findByEmail(update.getEmail().get()).isPresent();
            if(isEmailPresent) throw new BadRequestException("Email is already in use");

            user.setEmail(update.getEmail().get());
        }

        if(update.getPassword().isPresent()) { // Update password if present

            user.setPassword(passwordEncoder.encode(update.getPassword().get()));
        }

        return userMapper.mapUser(userRepository.save(user));
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN') or @authorization.isAccountOwner(authentication, #id)")
    @Transactional
    public void deleteById(Long id) {

        if(!userRepository.existsById(id)) throw new ResourceNotFoundException("No user with such identifier found");

        voteRepository.deleteByUserId(id); // Delete related votes
        pollService.deleteByCreatedBy(id); // Delete all created polls
        userRepository.deleteById(id); // Delete the user itself
    }
}
