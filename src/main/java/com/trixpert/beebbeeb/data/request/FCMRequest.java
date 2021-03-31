package com.trixpert.beebbeeb.data.request;

import lombok.Data;

@Data
public class FCMRequest {
    private long userId;
    private String title;
    private String message;
    private String channel;
}
