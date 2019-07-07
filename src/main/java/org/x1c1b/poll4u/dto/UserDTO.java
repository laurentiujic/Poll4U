package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "User account model")
public class UserDTO {

    @ApiModelProperty(value = "User's unique identifier")
    private Long id;

    @ApiModelProperty(value = "Account's username")
    private String username;

    @ApiModelProperty(value = "Account's email address")
    private String email;

    public UserDTO() { }

    public UserDTO(Long id, String username, String email) {

        this.id = id;
        this.username = username;
        this.email = email;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
