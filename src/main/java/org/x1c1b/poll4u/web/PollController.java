package org.x1c1b.poll4u.web;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;
import org.x1c1b.poll4u.service.PollService;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/polls")
@Api(tags = {"polls"})
public class PollController {

    private PollService pollService;

    @Autowired
    public PollController(PollService pollService) {

        this.pollService = pollService;
    }

    @GetMapping
    @ResponseBody
    @ApiOperation(value = "Get all polls")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched polls")
    })
    public ResponseEntity<Page<PollDTO>> findAll(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(pollService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "Get single poll")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched poll"),
            @ApiResponse(code = 404, message = "Poll doesn't exist")
    })
    public ResponseEntity<PollDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(pollService.findById(id));
    }

    @PostMapping
    @ResponseBody
    @ApiOperation(value = "Create new poll", authorizations = {@Authorization("Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created poll"),
            @ApiResponse(code = 400, message = "Validation failed, invalid model provided"),
            @ApiResponse(code = 401, message = "Unauthenticated access, authentication required")
    })
    public ResponseEntity<PollDTO> create(@Valid @RequestBody PollCreationDTO creation) {

        PollDTO poll = pollService.create(creation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location).body(poll);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    @ApiOperation(value = "Delete poll", authorizations = {@Authorization("Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully deleted account"),
            @ApiResponse(code = 404, message = "Poll doesn't exist"),
            @ApiResponse(code = 401, message = "Unauthenticated access, authentication required"),
            @ApiResponse(code = 403, message = "Missing privileges, access denied")
    })
    public ResponseEntity<PollDTO> deleteById(@PathVariable("id") Long id) {

        pollService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
