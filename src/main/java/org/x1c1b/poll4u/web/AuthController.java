package org.x1c1b.poll4u.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.x1c1b.poll4u.dto.CredentialsDTO;
import org.x1c1b.poll4u.dto.TokenDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.security.TokenProvider;
import org.x1c1b.poll4u.security.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@Api(tags = {"auth"})
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
    @ApiOperation(value = "Authenticate with a registered account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully authenticated"),
            @ApiResponse(code = 401, message = "Authentication failed because of bad credentials")
    })
    public ResponseEntity<TokenDTO> authenticate(@Valid @RequestBody CredentialsDTO credentials) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(),
                        credentials.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.generate(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        UserDTO user = new UserDTO(principal.getId(), principal.getUsername(), principal.getEmail());

        return ResponseEntity.ok(new TokenDTO(token, "Bearer", user));
    }
}
