package org.example.mvc.controller;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.mvc.RateLimit;
import org.example.mvc.vo.Message;
import org.example.mvc.vo.Text;
import org.example.mvc.vo.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
@RequestMapping("/users")
@Slf4j
public class MessagePushController {
    private static final String URL = "https://qyapi.weixin.qq.com/cgi-bin/webhook/send?key=c312f040-2360-47b6-91e3-289d217e9a51";
    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @RateLimit
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String hello(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("title", "客户信息登记");
        if (Strings.isNullOrEmpty(user.getName()) || Strings.isNullOrEmpty(user.getPhone())) {
            return "users/list";
        }

        Message message = new Message("text");
        Text text = new Text();
        text.setContent("[客户信息登记] "
                +  "\r客户经理： " + user.getManager()
                + "\r姓名： " + user.getName()
                +  "\r电话： " + user.getPhone()
                +  "\r办理业务： " + user.getAppointment()
        );
        message.setText(text);
        if (!Strings.isNullOrEmpty(user.getManager())) {
            message.setMentioned_list(List.of(user.getManager()));
        }

        String body = gson.toJson(message);
        System.out.println(body);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            // System.out.println(response.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "users/list";
    }
}
