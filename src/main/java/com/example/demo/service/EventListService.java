package com.example.demo.service;

import com.example.demo.domain.CalendarEvents;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@Service
public class EventListService {
    private final String HTTP_REQUEST_PRE = "https://www.googleapis.com/calendar/v3/calendars/";
    private final String HTTP_REQUEST_POST = "/events";

    public String getMyCalendarEventList(String accessToken, String encodedCalendarId){
        try {
            String jsonData = "";

            // Constructing the URL for the Google Calendar API request
            URL url = new URL(HTTP_REQUEST_PRE + encodedCalendarId + HTTP_REQUEST_POST +
                    "?access_token=" + accessToken
            );

            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            String line;
            while((line = bf.readLine()) != null){
                jsonData+=line;
            }

            return jsonData;

        } catch(Exception e) {
            return "error";
        }
    }
}
