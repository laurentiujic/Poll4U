package org.x1c1b.poll4u;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest
public class Poll4UApplicationTest {
	static {
		System.setProperty("poll4u.security.secret", "laur");
	}
	@Test
	public void contextLoads() {
	}

}
