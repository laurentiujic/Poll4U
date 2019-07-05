package org.x1c1b.poll4u.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.x1c1b.poll4u.model.Vote;

@Repository
public interface VoteRepository extends PagingAndSortingRepository<Vote, Long> {

    @Query("SELECT COUNT(v.id) from Vote v where v.choice.id = :choiceId")
    long countByChoiceId(@Param("choiceId") Long choiceId);

    @Query("SELECT COUNT(v.id) from Vote v where v.poll.id = :pollId")
    long countByPollId(@Param("pollId") Long pollId);

    boolean existsByPollIdAndUserId(Long pollId, Long userId);
}
