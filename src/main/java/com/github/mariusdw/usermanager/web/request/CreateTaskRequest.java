package com.github.mariusdw.usermanager.web.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CreateTaskRequest extends AbstractRequest {
    private String name;
    private String description;
    @JsonProperty("date_time")
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date dateTime;
}
