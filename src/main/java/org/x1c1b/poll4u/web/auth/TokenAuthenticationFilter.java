package org.x1c1b.poll4u.web.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.x1c1b.poll4u.security.TokenProvider;
import org.x1c1b.poll4u.security.UserPrincipalService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Autowired private TokenProvider<Long> tokenProvider;
    @Autowired private UserPrincipalService principalService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = extractToken(httpServletRequest);

            if (StringUtils.hasText(token) && tokenProvider.validate(token)) {

                Long userId = tokenProvider.getPayloadFromToken(token);

                UserDetails userDetails = principalService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception exc) {

            LOGGER.error("Could not set user authentication in security context", exc);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String extractToken(HttpServletRequest request) {

        String header = request.getHeader("Authorization");

        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {

            return header.substring(7);
        }

        LOGGER.error("Could not extract token, token is missing/malformed in request");
        return null;
    }
}
