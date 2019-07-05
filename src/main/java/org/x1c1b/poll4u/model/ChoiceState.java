package org.x1c1b.poll4u.model;

public class ChoiceState {

    private Long choiceId;
    private Long voteCount;

    public ChoiceState() { }

    public ChoiceState(Long choiceId, Long voteCount) {

        this.choiceId = choiceId;
        this.voteCount = voteCount;
    }

    public Long getChoiceId() {
        return choiceId;
    }

    public void setChoiceId(Long choiceId) {
        this.choiceId = choiceId;
    }

    public Long getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Long voteCount) {
        this.voteCount = voteCount;
    }
}
