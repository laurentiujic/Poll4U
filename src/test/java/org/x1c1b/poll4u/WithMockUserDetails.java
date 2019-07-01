package org.x1c1b.poll4u;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockUserDetailsSecurityContextFactory.class)
public @interface WithMockUserDetails {

    long id() default 1L;

    String username() default "user";

    String email() default "user@web.de";

    String password() default "Abc123";

    String[] roles() default {"ROLE_USER"};
}
