package org.x1c1b.poll4u.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.x1c1b.poll4u.security.UserPrincipal;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class AuditConfig {

    @Bean
    public AuditorAware<Long> auditorProvider() {

        return () -> {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(null == authentication ||
                    !authentication.isAuthenticated() ||
                    authentication instanceof AnonymousAuthenticationToken) {

                return Optional.empty();
            }

            UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();

            return Optional.ofNullable(principal.getId());
        };
    }
}