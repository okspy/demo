package org.example.mvc.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Message {
    @JsonProperty
    private String msgtype;
    @JsonProperty
    private Text text;

    @JsonProperty
    private List<String> mentioned_list;

    @JsonProperty
    private List<String> mentioned_mobile_list;

    public Message(String msgType) {
        this.msgtype = msgType;
    }
}
