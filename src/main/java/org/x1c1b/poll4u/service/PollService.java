package org.x1c1b.poll4u.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.x1c1b.poll4u.dto.PollCreationDTO;
import org.x1c1b.poll4u.dto.PollDTO;

import java.util.List;

public interface PollService {

    List<PollDTO> findAll();
    Page<PollDTO> findAll(Pageable pageable);
    Page<PollDTO> findByCreatedBy(Long userId, Pageable pageable);
    PollDTO findById(Long id);
    PollDTO create(PollCreationDTO creation);
    void deleteById(Long id);
}
