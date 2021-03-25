package com.trixpert.beebbeeb.data.response;

import lombok.Data;

import java.util.List;

@Data
public class SmsResponse {

    private String bulkId;
    private List<SmsResponseMessage> messages;

    @Data
    public static class SmsResponseMessage {
        private String to;
        private String messageId;
        private SmsStatus status;

        @Data
        public static class SmsStatus {
            private long groupId;
            private long id;
            private String groupName;
            private String name;
            private String description;
        }
    }
}
