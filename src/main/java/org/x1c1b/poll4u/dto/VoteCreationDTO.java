package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

@ApiModel(description = "Create vote request payload")
public class VoteCreationDTO {

    @NotNull(message = "Choice selection is required")
    @ApiModelProperty(value = "Existing choice's identifier")
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
