package org.x1c1b.poll4u.repository;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.x1c1b.poll4u.model.User;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/setup-users.sql"})
public class UserRepositoryTest {

    @Autowired private UserRepository userRepository;

    @Test public void saveUser() {

        User user = new User("dave", "dave@gmail.com", "Abc123");
        User expected = userRepository.save(user);

        assertNotNull(expected.getId());
        assertEquals("dave", expected.getUsername());
        assertEquals("dave@gmail.com", expected.getEmail());
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithInvalidEmail() {

        User user = new User("dave", "invalid", "Abc123");
        userRepository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithShortUsername() {

        User user = new User(RandomStringUtils.random(2), "dave@gmail.com", "Abc123");
        userRepository.save(user);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveUserWithLongUsername() {

        User user = new User(RandomStringUtils.random(20), "dave@gmail.com", "Abc123");
        userRepository.save(user);
    }

    @Test public void findById() {

        Optional<User> fetched = userRepository.findById(1L);

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals("user", fetched.get().getUsername());
        assertEquals("user@web.de", fetched.get().getEmail());
    }

    @Test public void findByUsername() {

        Optional<User> fetched = userRepository.findByUsername("user");

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals("user", fetched.get().getUsername());
        assertEquals("user@web.de", fetched.get().getEmail());
    }

    @Test public void findByEmail() {

        Optional<User> fetched = userRepository.findByEmail("user@web.de");

        assertTrue(fetched.isPresent());
        assertNotNull(fetched.get().getId());
        assertEquals("user", fetched.get().getUsername());
        assertEquals("user@web.de", fetched.get().getEmail());
    }
}