package org.x1c1b.poll4u.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.x1c1b.poll4u.dto.RegistrationDTO;
import org.x1c1b.poll4u.dto.UserDTO;
import org.x1c1b.poll4u.dto.UserUpdateDTO;
import org.x1c1b.poll4u.service.UserService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(userService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<UserDTO> create(@Valid @RequestBody RegistrationDTO registration) {

        UserDTO user = userService.create(registration);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(location).body(user);
    }

    @PutMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> updateById(@PathVariable("id") Long id, @Valid @RequestBody UserUpdateDTO update) {

        return ResponseEntity.ok(userService.updateById(id, update));
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<UserDTO> deleteById(@PathVariable("id") Long id) {

        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
