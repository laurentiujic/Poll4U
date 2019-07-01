package org.x1c1b.poll4u.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.x1c1b.poll4u.dto.CredentialsDTO;
import org.x1c1b.poll4u.dto.TokenDTO;
import org.x1c1b.poll4u.security.TokenProvider;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenProvider<Long> tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenProvider<Long> tokenProvider) {

        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody CredentialsDTO credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                        credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generate(authentication);
        return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
    }
}
