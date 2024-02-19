package com.example.demo.service;

import com.example.demo.domain.CalendarListItem;
import com.example.demo.domain.CalendarListResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CalendarListService {
    private final String HTTP_REQUEST = "https://www.googleapis.com/calendar/v3/users/me/calendarList";
    List<String> list = new ArrayList<String>();

    public List<String> getCalendarListId(String accessToken)
    {
        RestTemplate restTemplate = new RestTemplate();

        String apiUrl = HTTP_REQUEST + "?access_token=" + accessToken;
        ResponseEntity<CalendarListResponse> response = restTemplate.getForEntity(apiUrl, CalendarListResponse.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            CalendarListResponse responseBody = response.getBody();

            // items에 대한 처리. items는 사용자가 가지고 있는 캘린더 리스트
            for (CalendarListItem item : responseBody.getItems()) {
                list.add(item.getId());
            }
        } else {
            System.out.println("Error: " + response.getStatusCode());
        }

        return list;
    }
}
