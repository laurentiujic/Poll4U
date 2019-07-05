package org.x1c1b.poll4u.dto;

import javax.validation.constraints.NotNull;

public class VoteCreationDTO {

    @NotNull(message = "Choice selection is required")
    private Long choiceId;

    public VoteCreationDTO() { }

    public VoteCreationDTO(Long choiceId) {

        this.choiceId = choiceId;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }
}
