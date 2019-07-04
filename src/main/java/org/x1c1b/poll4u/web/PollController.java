package org.x1c1b.poll4u.web;

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
public class PollController {

    private PollService pollService;

    @Autowired
    public PollController(PollService pollService) {

        this.pollService = pollService;
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<Page<PollDTO>> findAll(@PageableDefault Pageable pageable) {

        return ResponseEntity.ok(pollService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PollDTO> findById(@PathVariable("id") Long id) {

        return ResponseEntity.ok(pollService.findById(id));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<PollDTO> create(@Valid @RequestBody PollCreationDTO creation) {

        PollDTO poll = pollService.create(creation);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(poll.getId()).toUri();

        return ResponseEntity.created(location).body(poll);
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<PollDTO> deleteById(@PathVariable("id") Long id) {

        pollService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
