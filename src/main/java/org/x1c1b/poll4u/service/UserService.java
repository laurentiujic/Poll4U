package org.x1c1b.poll4u.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.x1c1b.poll4u.model.User;

import java.util.List;

public interface UserService {

    List<User> findAll();
    Page<User> findAll(Pageable pageable);
    User findById(Long id);
    User findByUsername(String username);
    User findByEmail(String email);
    User create(User user);
    User updateById(Long id, User update);
    void deleteById(Long id);
}
