package org.x1c1b.poll4u.dto;

public class ChoiceDTO {

    private Long id;
    private String description;
    private Long voteCount;

    public ChoiceDTO() { }

    public ChoiceDTO(Long id, String description, Long voteCount) {

        this.id = id;
        this.description = description;
        this.voteCount = voteCount;
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

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
