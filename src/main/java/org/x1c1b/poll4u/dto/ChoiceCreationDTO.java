package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ApiModel(description = "Request payload for creating a choice")
public class ChoiceCreationDTO {

    @NotNull(message = "Description is required")
    @Size(max = 50, message = "Should contains not more than 50 characters")
    @ApiModelProperty(value = "Describe the selectable choice")
    private String description;

    public ChoiceCreationDTO() { }

    public ChoiceCreationDTO(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
