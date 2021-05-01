package com.trixpert.beebbeeb.data.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WokringTimsRegistrationRequest extends RegistrationRequest {
    private Time startDay;
    private Time endDay;
    private Time startHour;
    private Time endHour;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class Time{
        private String value ;
        private String view ;
    }


}



