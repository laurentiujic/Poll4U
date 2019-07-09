package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Default choice model")
public class ChoiceDTO {

    @ApiModelProperty(value = "Choice's unique identifier")
    private Long id;

    @ApiModelProperty(value = "Describes the choice")
    private String description;

    @ApiModelProperty(value = "Amount of votes for this choice")
    private Long votes;

    @ApiModelProperty(value = "Indicates if voted by current user")
    private boolean voted;

    public ChoiceDTO() { }

    public ChoiceDTO(Long id, String description, Long votes, boolean voted) {

        this.id = id;
        this.description = description;
        this.votes = votes;
        this.voted = voted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getVotes() {
        return votes;
    }

    public void setVotes(Long votes) {
        this.votes = votes;
    }

    public boolean isVoted() {
        return voted;
    }

    public void setVoted(boolean voted) {
        this.voted = voted;
    }
}
