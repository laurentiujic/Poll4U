package org.x1c1b.poll4u;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;
import org.x1c1b.poll4u.security.UserPrincipal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WithMockUserDetailsSecurityContextFactory implements WithSecurityContextFactory<WithMockUserDetails> {

    @Override
    public SecurityContext createSecurityContext(WithMockUserDetails details) {

        SecurityContext context = SecurityContextHolder.createEmptyContext();

        List<GrantedAuthority> authorities = Arrays.stream(details.roles())
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        UserPrincipal principal = new UserPrincipal(details.id(),
                details.username(), details.email(), details.password(), authorities);

        Authentication auth = new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
        context.setAuthentication(auth);
        return context;
    }
}
