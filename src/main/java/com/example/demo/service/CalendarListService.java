package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class CalendarListService {
    private final String HTTP_REQUEST = "https://www.googleapis.com/calendar/v3/users/me/calendarList";

    public String getCalendarList(String accessToken){
        try {
            String jsonData = "";

            URL url = new URL(HTTP_REQUEST + "?access_token=" + accessToken);

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
