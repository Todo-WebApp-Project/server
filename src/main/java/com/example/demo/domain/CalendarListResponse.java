package com.example.demo.domain;

import java.util.List;

public class CalendarListResponse {

    private String kind;
    private String etag;
    private String nextPageToken;
    private String nextSyncToken;
    private List<CalendarListItem> items;

    public CalendarListResponse()
    {
        super();
    }

    public CalendarListResponse(String kind, String etag, String nextPageToken, String nextSyncToken, List<CalendarListItem> items) {
        super();
        this.kind = kind;
        this.etag = etag;
        this.nextPageToken = nextPageToken;
        this.nextSyncToken = nextSyncToken;
        this.items = items;
    }


    public void setKind(String kind) {
        this.kind = kind;
    }

    public List<CalendarListItem> getItems() {
        return items;
    }

    public void setEtag(String etag) {
        this.etag = etag;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public void setNextSyncToken(String nextSyncToken) {
        this.nextSyncToken = nextSyncToken;
    }

    public void setItems(List<CalendarListItem> items) {
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public String getEtag() {
        return etag;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public String getNextSyncToken() {
        return nextSyncToken;
    }


}
