package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Optional;

@ApiModel(description = "User update request payload used for partial updates")
public class UserUpdateDTO {

    @ApiModelProperty(value = "The updated email address of the account")
    private String email;

    @ApiModelProperty(value = "The new passwod of the account")
    private String password;

    public UserUpdateDTO() { }

    public UserUpdateDTO(String email, String password) {

        this.email = email;
        this.password = password;
    }

    public Optional<@Email(message = "Should be a valid email address")
            @Size(max = 100, message = "Should contains not more than 100 characters")
            String> getEmail() {

        return Optional.ofNullable(email);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<@Size(max = 100, message = "Should contains not more than 100 characters")
            @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*[0-9[^A-Za-z0-9]]).{6,100}$", message = "Should contains minimum 6 characters and at least one letter and one number")
            String> getPassword() {

        return Optional.ofNullable(password);
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
