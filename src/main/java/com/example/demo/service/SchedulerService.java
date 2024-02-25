package com.example.demo.service;

import com.example.demo.domain.Performance;
import com.example.demo.domain.Todo;
import com.example.demo.domain.User;
import com.example.demo.repository.PerformanceRepository;
import com.example.demo.repository.TodoRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.time.LocalDate;
import java.util.List;


@Service
@EnableScheduling
public class SchedulerService {

    UserRepository userRepository;
    TodoRepository todoRepository;
    PerformanceRepository performanceRepository;

    @Autowired
    public SchedulerService(UserRepository userRepository, TodoRepository todoRepository,PerformanceRepository performanceRepository)
    {
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
        this.performanceRepository = performanceRepository;
    }


    @Scheduled(cron = "0 50 23 * * *")
    public void executePerformanceUpdate()
    {
        List<User> userList = userRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for(User user : userList)
        {
            List<Todo> todayTodos = todoRepository.findByUserAndTodoDate(user,currentDate);

            if(todayTodos.isEmpty())
                continue;

            boolean isAllTodayTodosFinished = todayTodos.stream()
                    .allMatch( Todo -> Todo.getFinish() == 1);

            Performance performance = new Performance();

            Optional<Long> maxPerformId = performanceRepository.findMaxPerformId();

            //perform 테이블의 첫번째 레코드인 경우
            if(maxPerformId.isEmpty())
                performance.setPerform_id(0);
            else
                performance.setPerform_id(maxPerformId.get()+1);

            performance.setPerform_date(LocalDate.now());
            performance.setUser(performance.getUser());


            if(isAllTodayTodosFinished)
                performance.setFinish(1);
            else
                performance.setFinish(0);

            performanceRepository.save(performance);
        }


    }
}
