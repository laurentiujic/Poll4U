package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ApiModel(description = "Payload for creating a new poll")
public class PollCreationDTO {

    @NotNull(message = "Question is required")
    @Size(max = 140, message = "Should contains not more than 140 characters")
    @ApiModelProperty(value = "Question of the poll")
    private String question;

    @NotNull(message = "Choices are required")
    @Size(min = 2, max = 6, message = "Only between 2 and 6 choice options are possible")
    @Valid
    @ApiModelProperty(value = "Poll related choices for answering the question")
    private List<ChoiceCreationDTO> choices;

    @NotNull(message = "Expiration date is required")
    @ApiModelProperty(value = "Date of poll's expiration")
    private Date expiration;

    public PollCreationDTO() {

        this.choices = new ArrayList<>();
    }

    public PollCreationDTO(String question, List<ChoiceCreationDTO> choices, Date expiration) {

        this.question = question;
        this.choices = choices;
        this.expiration = expiration;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceCreationDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceCreationDTO> choices) {
        this.choices = choices;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }
}
