package org.x1c1b.poll4u.model;

import org.x1c1b.poll4u.model.audit.UserDateAudit;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "polls")
public class Poll extends UserDateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Question is required")
    @Size(max = 140, message = "Should contains not more than 140 characters")
    private String question;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL,
            fetch = FetchType.EAGER, orphanRemoval = true)
    @Size(min = 2, max = 6, message = "Only between 2 and 6 choice options are possible")
    private List<Choice> choices;

    @NotNull(message = "Expiration date is required")
    private Date expiration;

    public Poll() {

        this.choices = new ArrayList<>();
    }

    public Poll(String question) {

        this();

        this.question = question;
    }

    public Poll(String question, List<Choice> choices, Date expiration) {

        this(question);

        this.choices = choices;
        this.expiration = expiration;
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

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public Date getExpiration() {
        return expiration;
    }

    public void setExpiration(Date expiration) {
        this.expiration = expiration;
    }

    public void addChoice(Choice choice) {

        this.choices.add(choice);
        choice.setPoll(this);
    }

    public void removeChoice(Choice choice) {

        this.choices.remove(choice);
        choice.setPoll(null);
    }
}
