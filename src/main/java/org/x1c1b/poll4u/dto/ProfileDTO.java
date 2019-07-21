package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User profile model")
public class ProfileDTO {

    @ApiModelProperty(value = "User's unique identifier")
    private Long id;

    @ApiModelProperty(value = "Account's username")
    private String username;

    public ProfileDTO() { }

    public ProfileDTO(Long id, String username) {

        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
