package org.x1c1b.poll4u.web.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TokenAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest httpServletRequest,
                         HttpServletResponse httpServletResponse,
                         AuthenticationException exc) throws IOException {

        LOGGER.error("Responding with unauthorized error. Message - {}", exc.getMessage());
        httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, exc.getMessage());
    }
}
