package org.x1c1b.poll4u.service;

import org.x1c1b.poll4u.dto.VoteCreationDTO;

public interface VoteService {

    void voteForPoll(Long userId, Long pollId, VoteCreationDTO creation);
}
