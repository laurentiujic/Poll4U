package org.x1c1b.poll4u.security;

import org.springframework.security.core.Authentication;

public interface TokenProvider<P> {

    String generate(Authentication authentication);
    boolean validate(String token);
    P getPayloadFromToken(String token);
}
