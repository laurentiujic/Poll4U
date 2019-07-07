package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "User update request payload")
public class UserUpdateDTO {

    @NotNull(message = "Identifier is required")
    @ApiModelProperty(value = "Immutable user's identifier", readOnly = true)
    private Long id;

    @NotNull(message = "Username is required")
    @Size(min = 4, max = 15, message = "Should consist out of minimum 4 and maxmum 15 characters")
    @ApiModelProperty(value = "Immutable username", readOnly = true)
    private String username;

    @Email(message = "Should be a valid email address")
    @Size(max = 100, message = "Should contains not more than 100 characters")
    @NotNull(message = "Email is required")
    @ApiModelProperty(value = "The updated email address of the account")
    private String email;

    @NotNull(message = "Password is required")
    @Size(max = 100, message = "Should contains not more than 100 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Should contains minimum 6 characters and at least one letter and one number")
    @ApiModelProperty(value = "The new passwod of the account")
    private String password;

    public UserUpdateDTO() { }

    public UserUpdateDTO(Long id, String username, String email, String password) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
