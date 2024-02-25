package com.example.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Performance {

    @Id
    long perform_id;
    String user_id;
    LocalDate perform_date;
    int finish;

    public Performance()
    {
    }

    public Performance(long perform_id, String user_id, LocalDate perform_date, int finish) {
        this.perform_id = perform_id;
        this.user_id = user_id;
        this.perform_date = perform_date;
        this.finish = finish;
    }

    public long getPerform_id() {
        return perform_id;
    }

    public void setPerform_id(long perform_id) {
        this.perform_id = perform_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public LocalDate getPerform_date() {
        return perform_date;
    }

    public void setPerform_date(LocalDate perform_date) {
        this.perform_date = perform_date;
    }

    public int getFinish() {
        return finish;
    }

    public void setFinish(int finish) {
        this.finish = finish;
    }
}
