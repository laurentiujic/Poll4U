package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@ApiModel(description = "Registration request payload")
public class RegistrationDTO {

    @NotNull(message = "Username is required")
    @Size(min = 4, max = 15, message = "Should consist out of minimum 4 and maximum 15 characters")
    @ApiModelProperty(value = "Username of new account")
    private String username;

    @Email(message = "Should be a valid email address")
    @Size(max = 100, message = "Should contains not more than 100 characters")
    @NotNull(message = "Email is required")
    @ApiModelProperty(value = "Email to register with")
    private String email;

    @NotNull(message = "Password is required")
    @Size(max = 100, message = "Should contains not more than 100 characters")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Should contains minimum 6 characters and at least one letter and one number")
    @ApiModelProperty(value = "Password used for authentication")
    private String password;

    public RegistrationDTO() { }

    public RegistrationDTO(String username, String email, String password) {

        this.username = username;
        this.email = email;
        this.password = password;
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
