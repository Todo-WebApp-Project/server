package com.example.demo.domain;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Performance {

    @Id
    private long perform_id;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
    //String user_id;
    private LocalDate perform_date;
    private int finish;


    
	public Performance()
    {
    }

    public Performance(long perform_id, User user, LocalDate perform_date, int finish) {
		super();
		this.perform_id = perform_id;
		this.user = user;
		this.perform_date = perform_date;
		this.finish = finish;
	}



	public long getPerform_id() {
        return perform_id;
    }

    public void setPerform_id(long perform_id) {
        this.perform_id = perform_id;
    }



    public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
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



	@Override
	public String toString() {
		return "Performance [perform_id=" + perform_id + ", user=" + user + ", perform_date=" + perform_date
				+ ", finish=" + finish + "]";
	}
}
