package org.x1c1b.poll4u.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.x1c1b.poll4u.model.User;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test public void saveUser() {

        User user = new User("user", "user@web.de", "Abc123");
        User expected = userRepository.save(user);

        Optional<User> fetched = userRepository.findByUsername("user");

        assertTrue(fetched.isPresent());
        assertEquals(expected.getUsername(), fetched.get().getUsername());
        assertEquals(expected.getEmail(), fetched.get().getEmail());
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithInvalidEmail() {

        User user = new User("user", "invalid", "Abc123");
        userRepository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithShortUsername() {

        User user = new User(RandomStringUtils.random(2), "user@web.de", "Abc123");
        userRepository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithLongUsername() {

        User user = new User(RandomStringUtils.random(20), "user@web.de", "Abc123");
        userRepository.save(user);
    }

    @Test public void findById() {

        User user = new User("user", "user@web.de", "Abc123");
        User expected = userRepository.save(user);

        Optional<User> fetched = userRepository.findById(expected.getId());

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals(expected.getUsername(), fetched.get().getUsername());
        assertEquals(expected.getEmail(), fetched.get().getEmail());
    }

    @Test public void findByUsername() {

        User user = new User("user", "user@web.de", "Abc123");
        User expected = userRepository.save(user);

        Optional<User> fetched = userRepository.findByUsername("user");

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals(expected.getUsername(), fetched.get().getUsername());
        assertEquals(expected.getEmail(), fetched.get().getEmail());
    }

    @Test public void findByEmail() {

        User user = new User("user", "user@web.de", "Abc123");
        User expected = userRepository.save(user);

        Optional<User> fetched = userRepository.findByEmail("user@web.de");

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals(expected.getUsername(), fetched.get().getUsername());
        assertEquals(expected.getEmail(), fetched.get().getEmail());
    }
}