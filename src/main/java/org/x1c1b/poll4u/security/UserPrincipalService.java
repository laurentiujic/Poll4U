package org.x1c1b.poll4u.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.model.User;
import org.x1c1b.poll4u.repository.UserRepository;

import javax.transaction.Transactional;

@Service
public class UserPrincipalService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserPrincipalService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username));

        return new UserPrincipal(user);
    }

    @Transactional
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id));

        return new UserPrincipal(user);
    }
}
