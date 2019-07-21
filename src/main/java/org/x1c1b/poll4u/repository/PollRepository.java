package org.x1c1b.poll4u.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.x1c1b.poll4u.model.Poll;

import java.util.List;

@Repository
public interface PollRepository extends PagingAndSortingRepository<Poll, Long> {

    List<Poll> findByCreatedBy(Long userId);
    Page<Poll> findByCreatedBy(Long userId, Pageable pageable);
    List<Poll> findByIdIn(List<Long> pollIds);
}
