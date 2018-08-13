package com.github.mariusdw.usermanager.web.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UpdateTaskRequest extends AbstractRequest {
    private String name;
    private String description;
    @JsonProperty("date_time")
    private Date dateTime;
}
