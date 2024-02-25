package com.example.demo.domain;

import java.util.List;
public class CalendarEvents {
    private String kind;
    private String etag;
    private String summary;
    private String description;
    private String updated;
    private String accessRole;
    private List<DefaultReminder> defaultReminders;
    private List<EventListItem> items;

    public static class DefaultReminder {
        private String method;
        private int minutes;

        public DefaultReminder()
        {
        }

        public DefaultReminder(String method,int minutes) {
            this.method = method;
            this.minutes=minutes;
        }

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public int getMinutes() {
            return minutes;
        }

        public void setMinutes(int minutes) {
            this.minutes = minutes;
        }
    }

    public CalendarEvents()
    {
        super();
    }

    public CalendarEvents(String kind, String etag, String summary,
                          String description, String updated, String accessRole,
                          List<DefaultReminder> defaultReminders,
                          List<EventListItem> items)
    {
        super();
        this.kind = kind;
        this.etag = etag;
        this.summary = summary;
        this.description = description;
        this.updated = updated;
        this.accessRole = accessRole;
        this.defaultReminders = defaultReminders;
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

    public String getAccessRole() {
        return accessRole;
    }

    public void setAccessRole(String accessRole) {
        this.accessRole = accessRole;
    }

    public List<DefaultReminder> getDefaultReminders() {
        return defaultReminders;
    }

    public void setDefaultReminders(List<DefaultReminder> defaultReminders) {
        this.defaultReminders = defaultReminders;
    }

    public List<EventListItem> getItems() {
        return items;
    }

    public void setItems(List<EventListItem> items) {
        this.items = items;
    }
}
