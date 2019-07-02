package org.x1c1b.poll4u.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "choices")
public class Choice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Description is required")
    @Size(max = 50, message = "Should contains not more than 50 characters")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "poll_id", nullable = false)
    private Poll poll;

    @OneToMany(mappedBy = "choice", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Vote> votes;

    public Choice() {

        this.votes = new ArrayList<>();
    }

    public Choice(String description) {

        this();

        this.description = description;
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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public List<Vote> getVotes() {
        return votes;
    }

    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    public void addVote(Vote vote) {

        this.votes.add(vote);
        vote.setChoice(this);
    }

    public void removeVote(Vote vote) {

        this.votes.remove(vote);
        vote.setChoice(null);
    }
}
