package org.x1c1b.poll4u.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.x1c1b.poll4u.dto.ProfileDTO;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;

import java.util.List;

public interface UserService {

    List<ProfileDTO> findAll();
    Page<ProfileDTO> findAll(Pageable pageable);
    ProfileDTO findById(Long id);
    ProfileDTO findByUsername(String username);
    ProfileDTO findByEmail(String email);
    UserDTO create(RegistrationDTO registration);
    UserDTO updateById(Long id, UserUpdateDTO update);
    void deleteById(Long id);
}
