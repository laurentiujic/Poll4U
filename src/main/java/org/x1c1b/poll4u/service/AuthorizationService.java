package org.x1c1b.poll4u.service;

import org.springframework.security.core.Authentication;

public interface AuthorizationService {

    boolean isAccountOwner(Authentication authentication, Long accountId);

    boolean isPollCreator(Authentication authentication, Long pollId);
}
