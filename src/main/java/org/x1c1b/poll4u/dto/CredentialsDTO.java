package org.x1c1b.poll4u.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CredentialsDTO {

    @NotNull(message = "Username is required")
    @Size(min = 4, max = 15, message = "Should consist out of minimum 4 and maxmum 15 characters")
    private String username;

    @NotNull(message = "Password is required")
    @Size(max = 100, message = "Should contains not more than 100 characters")
    private String password;

    public CredentialsDTO() { }

    public CredentialsDTO(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
