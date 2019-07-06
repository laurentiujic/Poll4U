package org.x1c1b.poll4u.web;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.x1c1b.poll4u.dto.VoteCreationDTO;
import org.x1c1b.poll4u.security.UserPrincipal;
import org.x1c1b.poll4u.service.VoteService;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/polls/{pollId}/votes")
@Api(tags = {"votes"})
public class VoteController {

    private VoteService voteService;

    @Autowired
    public VoteController(VoteService voteService) {

        this.voteService = voteService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Vote for poll", authorizations = {@Authorization("Bearer")})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Successfully voted"),
            @ApiResponse(code = 404, message = "Poll or choice doesn't exist"),
            @ApiResponse(code = 401, message = "Unauthenticated access, authentication required"),
            @ApiResponse(code = 409, message = "User already voted for this poll"),
            @ApiResponse(code = 410, message = "Poll already expired")
    })
    public void voteForPoll(@PathVariable("pollId") Long pollId,
                                      @AuthenticationPrincipal @ApiIgnore UserPrincipal principal,
                                      @Valid @RequestBody VoteCreationDTO creation) {

        voteService.voteForPoll(principal.getId(), pollId, creation);
    }
}
