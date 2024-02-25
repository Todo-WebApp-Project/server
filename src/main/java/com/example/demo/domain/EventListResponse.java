package com.example.demo.domain;

import java.util.List;

public class EventListResponse {


    private String kind;
    private String etag;
    private String summary;
    private String description;
    private String updated;
    private String timeZone;
    private String accessRole;
    private List<Reminder> defaultReminders;
    private String nextPageToken;
    private String nextSyncToken;
    private List<EventListItem> items;

    public EventListResponse() {
        super();
    }

    public EventListResponse(String kind, String etag,
                             String summary, String description, String updated, String timeZone,
                             String accessRole, List<Reminder> defaultReminders, String nextPageToken,
                             String nextSyncToken, List<EventListItem> items)
    {
        super();
        this.kind = kind;
        this.etag = etag;
        this.summary = summary;
        this.description = description;
        this.updated = updated;
        this.timeZone = timeZone;
        this.accessRole = accessRole;
        this.defaultReminders = defaultReminders;
        this.nextPageToken = nextPageToken;
        this.nextSyncToken = nextSyncToken;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getEtag() {
        return etag;
    }

    public void setEtag(String etag) {
        this.etag = etag;
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

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public List<Reminder> getDefaultReminders() {
        return defaultReminders;
    }

    public void setDefaultReminders(List<Reminder> defaultReminders) {
        this.defaultReminders = defaultReminders;
    }

    public List<EventListItem> getItems() {
        return items;
    }

    public void setItems(List<EventListItem> items) {
        this.items = items;
    }

    public static class Reminder {
        private String method;
        private int minutes;

    }

}
