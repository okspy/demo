package org.example.mvc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Text {
    @JsonProperty
    private String content;
    @JsonProperty
    private List<String> mentioned_list;

    @JsonProperty
    private List<String> mentioned_mobile_list;
}
