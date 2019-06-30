package org.x1c1b.poll4u.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
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
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {

        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<UserDTO> findAll() {

        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> findAll(Pageable pageable) {

        return userRepository.findAll(pageable).map((user) -> modelMapper.map(user, UserDTO.class));
    }

    @Override
    public UserDTO findById(Long id) {

        return modelMapper.map(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found")),
                UserDTO.class);
    }

    @Override
    public UserDTO findByUsername(String username) {

        return modelMapper.map(userRepository.findByUsername(username).orElseThrow(
                () -> new ResourceNotFoundException("No user with such username found")),
                UserDTO.class);
    }

    @Override
    public UserDTO findByEmail(String email) {

        return modelMapper.map(userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("No user with such email found")),
                UserDTO.class);
    }

    @Override
    public UserDTO create(RegistrationDTO registration) {

        boolean isNamePresent = userRepository.findByUsername(registration.getUsername()).isPresent();
        boolean isEmailPresent = userRepository.findByEmail(registration.getEmail()).isPresent();

        if(isNamePresent) throw new BadRequestException("Username is already present");
        if(isEmailPresent) throw new BadRequestException("Email is already in use");

        Role role = roleRepository.findByName(Role.RoleName.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("No such role found"));

        User user = modelMapper.map(registration, User.class);

        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.setRoles(Collections.singleton(role));

        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public UserDTO updateById(Long id, UserUpdateDTO update) {

        User user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("No user with such identifier found"));

        boolean isEmailPresent = userRepository.findByEmail(user.getEmail()).isPresent();
        if(isEmailPresent) throw new BadRequestException("Email is already in use");

        /*
        "Be strict in what you produces and be tolerant in what you consumes", ignore additional
        fields of entity. Just update partially the updatable fields.
         */

        if(!user.getId().equals(update.getId())) throw new BadRequestException("Identifier isn't mutable");
        if(!user.getUsername().equals(update.getUsername())) throw new BadRequestException("Username isn't mutable");

        user.setPassword(passwordEncoder.encode(update.getPassword()));
        user.setEmail(update.getEmail());

        return modelMapper.map(userRepository.save(user), UserDTO.class);
    }

    @Override
    public void deleteById(Long id) {

        if(!userRepository.existsById(id)) throw new ResourceNotFoundException("No user with such identifier found");
        userRepository.deleteById(id);
    }
}
