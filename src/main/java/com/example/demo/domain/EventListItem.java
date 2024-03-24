package com.example.demo.domain;

import java.util.List;

public class EventListItem {

    private String kind;
    private String id;
    private String status;
    private String htmlLink;
    private String created;
    private String updated;
    private String summary;
    private String description;
    private Creator creator;

    public EventListItem()
    {
    }

    public EventListItem(String kind, String id, String status,
                         String htmlLink, String created, String updated,
                         String summary, String description, Creator creator)
    {
        this.kind = kind;
        this.id = id;
        this.status = status;
        this.htmlLink = htmlLink;
        this.created = created;
        this.updated = updated;
        this.summary = summary;
        this.description = description;
        this.creator = creator;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHtmlLink() {
        return htmlLink;
    }

    public void setHtmlLink(String htmlLink) {
        this.htmlLink = htmlLink;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Creator getCreator() {
        return creator;
    }

    public void setCreator(Creator creator) {
        this.creator = creator;
    }
}


class Creator {
    private String id;
    private String email;
    private String displayName;
    private boolean self;

    public Creator() {
    }

    public Creator(String id, String email, String displayName, boolean self) {
        this.id = id;
        this.email = email;
        this.displayName = displayName;
        this.self = self;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isSelf() {
        return self;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setSelf(boolean self) {
        this.self = self;
    }
}







