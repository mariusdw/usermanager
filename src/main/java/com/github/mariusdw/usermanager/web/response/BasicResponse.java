package com.github.mariusdw.usermanager.web.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BasicResponse {
    private Integer code;
    private String description;

    public BasicResponse(ResponseCode responseCode) {
        this.code = responseCode.getCode();
        this.description = responseCode.getDescription();
    }
}
