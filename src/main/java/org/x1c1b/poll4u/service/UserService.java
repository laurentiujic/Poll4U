package org.x1c1b.poll4u.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> findAll();
    Page<UserDTO> findAll(Pageable pageable);
    UserDTO findById(Long id);
    UserDTO findByUsername(String username);
    UserDTO findByEmail(String email);
    UserDTO create(RegistrationDTO registration);
    UserDTO updateById(Long id, UserUpdateDTO update);
    void deleteById(Long id);
}
