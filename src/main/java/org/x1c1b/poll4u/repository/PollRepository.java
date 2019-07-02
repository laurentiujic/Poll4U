package org.x1c1b.poll4u.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.x1c1b.poll4u.model.Poll;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {

}
