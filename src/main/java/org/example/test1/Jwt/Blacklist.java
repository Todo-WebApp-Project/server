package org.example.test1.Jwt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class Blacklist {
    @Id
    private String token;

    @Column(name="delete_at")
    private Date deleteAt;

    public Blacklist() {

    }

    public Blacklist(String token, Date deleteAt) {
        this.token = token;
        this.deleteAt = deleteAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(Date deleteAt) {
        this.deleteAt = deleteAt;
    }

    @Override
    public String toString() {
        return "Blacklist{" +
                "token='" + token + '\'' +
                ", deleteAt=" + deleteAt +
                '}';
    }
}
