package org.example.mvc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    @JsonProperty
    private String name;
    @JsonProperty
    private String phone;

    @JsonProperty
    private String manager;

    @JsonProperty
    private String appointment;
}
