package com.example.demo.controller;

import com.example.demo.domain.CalendarEvents;
import com.example.demo.domain.EventListItem;
import com.example.demo.service.CalendarListService;
import com.example.demo.service.EventListService;
import com.example.demo.service.RestJsonService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    private final String ENCODED_HOLIDAY_CALENDAR_ID = "ko.south_korea%23holiday%40group.v.calendar.google.com";
    private final String ENCODED_BIRTHDAY_CALENDAR_ID = "addressbook%23contacts%40group.v.calendar.google.com";

    CalendarListService calendarListService;
    EventListService eventListService;
    String accessToken;     //구글 OAuth 2.0 인증 후 받은 사용자의 accessToken

    @Autowired
    public HomeController(CalendarListService calendarListService, EventListService eventListService) {
        super();
        this.calendarListService = calendarListService;
        this.eventListService = eventListService;
    }


    @GetMapping("/calendar")
    public String calendar(Model model) {
        return "calendar";
    }


    @GetMapping("/test")
    public String test(@RequestParam("code") String code, Model model) {
        RestJsonService restJsonService = new RestJsonService();
        String accessTokenJsonData = restJsonService.getAccessTokenJsonData(code);

        JSONObject accessTokenjsonObject = null;
        try {
            accessTokenjsonObject = new JSONObject(accessTokenJsonData);
            accessToken = accessTokenjsonObject.get("access_token").toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        // '구글 캘린더'에서 캘린더 리스트 id를 가져오기
        List<String> calendarIdList = calendarListService.getCalendarListId(accessToken);
        if (calendarIdList.isEmpty()) return "error";


        //calendarList에서 캘린더의 id하나씩 가져와서 캘린더 조회.
        for (String id : calendarIdList) {
            try {
                /*
                 아직 못한것 : EventListService.java에서 Json 문자열을 클래스에 매핑해야 됌.
                 */
                String eventJsonData = eventListService.getMyCalendarEventList(accessToken, URLEncoder.encode(id, StandardCharsets.UTF_8.toString()));
                System.out.println("User Json Data : " + eventJsonData);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return "success";
    }
}
