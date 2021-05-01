package com.trixpert.beebbeeb.data.request;

import lombok.Data;

import java.util.List;

@Data
public class SmsRequest {

    private List<SmsMessage> messages;

    @Data
    public static class SmsMessage {
        private String from;
        private List<Destination> destinations;
        private String text;

        @Data
        public static class Destination {
            private String to;
        }

    }

}
