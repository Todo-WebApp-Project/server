package com.example.demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.example.demo.domain.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.repository.ScheduleRepository;
import com.example.demo.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Schedule;

@Service
public class EventListService {
    private final String HTTP_REQUEST_PRE = "https://www.googleapis.com/calendar/v3/calendars/";
    private final String HTTP_REQUEST_POST = "/events";

    private UserRepository userRepository;
    private ScheduleRepository scheduleRepository;


    public EventListService(UserRepository userRepository, ScheduleRepository scheduleRepository){
        this.userRepository = userRepository;
        this.scheduleRepository = scheduleRepository;
    }
    public void getMyCalendarEventList(String user_id,String accessToken, String encodedCalendarId){
        try {
            String jsonData = "";
            List<Schedule> scheduleList = new ArrayList<>();

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

            JsonNode rootNode =  new ObjectMapper().readTree(jsonData);
            JsonNode itemsNode = rootNode.get("items");

            Optional<User> optionalUserFindByUserId = userRepository.findById(user_id);


            for (JsonNode item : itemsNode) {
                String creatorEmail = item.get("creator").get("email").asText();

                if(optionalUserFindByUserId.isPresent()){
                    if(!creatorEmail.equalsIgnoreCase(optionalUserFindByUserId.get().getEmail()))
                        continue;
                }else
                {
                    return;
                }

                String startDateTime = item.get("start").get("dateTime").asText();
                startDateTime = startDateTime.substring(0,startDateTime.indexOf('T'));
                String endDateTime = item.get("end").get("dateTime").asText();
                endDateTime = endDateTime.substring(0,endDateTime.indexOf('T'));
                String summary = item.get("summary").asText();

                Optional<User> optionalUserFindByEmail = userRepository.findByEmail(creatorEmail);
                if (optionalUserFindByEmail.isPresent()) {
                    User user = optionalUserFindByEmail.get();

                    Schedule schedule = new Schedule();
                    schedule.setUser(user);
                    schedule.setStart_date(LocalDate.parse(startDateTime));
                    System.out.println(LocalDate.parse(startDateTime));
                    schedule.setEnd_date(LocalDate.parse(endDateTime));
                    schedule.setPlan(summary);
                    schedule.setId(30010);
                    schedule.setColor("dcdcdc");


                    scheduleRepository.save(schedule);
                } else {
                    throw new UserNotFoundException("User with email " + creatorEmail + " not found.");
                }
            }

        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}