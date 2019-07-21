package org.x1c1b.poll4u.web;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.x1c1b.poll4u.dto.ProfileDTO;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
import org.x1c1b.poll4u.service.UserService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
@Api(tags = {"users"})
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "Get all user accounts")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched accounts")
    })
    public ResponseEntity<Page<ProfileDTO>> findAll(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "Get single user account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched account"),
            @ApiResponse(code = 404, message = "Account doesn't exist")
    })
    public ResponseEntity<ProfileDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create new user account")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created account"),
            @ApiResponse(code = 400, message = "Validation failed, invalid model provided")
    })
    public ResponseEntity<ProfileDTO> create(@Valid @RequestBody RegistrationDTO registration) {

        ProfileDTO user = userService.create(registration);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "Update account of current user", authorizations = {@Authorization("Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated account"),
            @ApiResponse(code = 404, message = "Account doesn't exist"),
            @ApiResponse(code = 400, message = "Validation failed, invalid model provided"),
            @ApiResponse(code = 401, message = "Unauthenticated access, authentication required"),
            @ApiResponse(code = 403, message = "Missing privileges, access denied")
    })
    public ResponseEntity<ProfileDTO> updateById(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO update) {

        return ResponseEntity.ok(userService.updateById(id, update));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete account of current user", authorizations = {@Authorization("Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted account"),
            @ApiResponse(code = 404, message = "Account doesn't exist"),
            @ApiResponse(code = 401, message = "Unauthenticated access, authentication required"),
            @ApiResponse(code = 403, message = "Missing privileges, access denied")
    })
    public void deleteById(@PathVariable("id") Long id) {

        userService.deleteById(id);
    }
}
