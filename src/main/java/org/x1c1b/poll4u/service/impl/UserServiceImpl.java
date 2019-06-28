package org.x1c1b.poll4u.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.error.BadRequestException;
import org.x1c1b.poll4u.error.ResourceNotFoundException;
import org.x1c1b.poll4u.model.Role;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.RoleRepository;
import org.x1c1b.poll4u.repository.UserRepository;
import org.x1c1b.poll4u.service.UserService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> findAll() {

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public Page<User> findAll(Pageable pageable) {

        return userRepository.findAll(pageable);
    }

    @Override
    public User findById(Long id) {

        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found"));
    }

    @Override
    public User findByUsername(String username) {

        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("No user with such username found"));
    }

    @Override
    public User findByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("No user with such email address found"));
    }

    @Override
    public User create(User user) {

        boolean isNamePresent = userRepository.findByUsername(user.getUsername()).isPresent();
        boolean isEmailPresent = userRepository.findByEmail(user.getEmail()).isPresent();

        if(isNamePresent) throw new BadRequestException("Username is already present");
        if(isEmailPresent) throw new BadRequestException("Email is already in use");

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("No such role found"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singleton(role));

        return userRepository.save(user);
    }

    @Override
    public User updateById(Long id, User update) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found"));

        boolean isEmailPresent = userRepository.findByEmail(user.getEmail()).isPresent();
        if(isEmailPresent) throw new BadRequestException("Email is already in use");

        /*
        "Be strict in what you produces and be tolerant in what you consumes", ignore additional
        fields of entity. Just update partially the updatable fields.
         */

        user.setEmail(update.getEmail());
        user.setPassword(passwordEncoder.encode(update.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {

        if(!userRepository.existsById(id)) throw new ResourceNotFoundException("No user with such identifier found");
        userRepository.deleteById(id);
    }
}
