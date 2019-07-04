package org.x1c1b.poll4u.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.x1c1b.poll4u.model.Poll;
import org.x1c1b.poll4u.repository.PollRepository;
import org.x1c1b.poll4u.security.UserPrincipal;
import org.x1c1b.poll4u.service.AuthorizationService;

@Service("authorization")
public class AuthorizationServiceImpl implements AuthorizationService {

    private PollRepository pollRepository;

    @Autowired
    public AuthorizationServiceImpl(PollRepository pollRepository) {

        this.pollRepository = pollRepository;
    }

    @Override
    public boolean isAccountOwner(Authentication authentication, Long accountId) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

        return principal.getId().equals(accountId);
    }

    @Override
    public boolean isPollCreator(Authentication authentication, Long pollId) {

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Poll poll = pollRepository.findById(pollId).orElse(null);

        return null != poll && principal.getId().equals(poll.getCreatedBy());
    }
}
