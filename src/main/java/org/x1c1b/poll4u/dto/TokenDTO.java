package org.x1c1b.poll4u.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Token response model")
public class TokenDTO {

    @ApiModelProperty(value = "The generated token used for authentication")
    private String token;

    @ApiModelProperty(value = "Generated token's type", example = "Bearer")
    private String type;

    public TokenDTO() { }

    public TokenDTO(String token, String type) {

        this.token = token;
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
