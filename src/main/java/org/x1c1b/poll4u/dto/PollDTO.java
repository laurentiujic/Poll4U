package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel(description = "Default poll model")
public class PollDTO {

    @ApiModelProperty(value = "Poll's unique identifier")
    private Long id;

    @ApiModelProperty(value = "Question of the poll")
    private String question;

    @ApiModelProperty(value = "Selectable choices for answering the question")
    private List<ChoiceDTO> choices;

    @ApiModelProperty(value = "Date of poll's expiration")
    private Date expiration;

    @ApiModelProperty(value = "Identifier of poll's creator")
    private Long createdBy;

    @ApiModelProperty(value = "Identifier of last user that updated the poll")
    private Long updatedBy;

    @ApiModelProperty(value = "Creation time of poll")
    private Date createdAt;

    @ApiModelProperty(value = "Last time poll was updated")
    private Date updatedAt;

    public PollDTO() { }

    public PollDTO(Long id, String question, List<ChoiceDTO> choices, Date expiration,
                   Long createdBy, Long updatedBy, Date createdAt, Date updatedAt) {

        this.id = id;
        this.question = question;
        this.choices = choices;
        this.expiration = expiration;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<ChoiceDTO> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceDTO> choices) {
        this.choices = choices;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
