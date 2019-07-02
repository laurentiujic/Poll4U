package org.x1c1b.poll4u.dto;

public class ChoiceDTO {

    private Long id;
    private String description;

    public ChoiceDTO() { }

    public ChoiceDTO(Long id, String description) {

        this.id = id;
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
}
