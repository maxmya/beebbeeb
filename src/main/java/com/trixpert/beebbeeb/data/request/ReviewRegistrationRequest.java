package com.trixpert.beebbeeb.data.request;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRegistrationRequest {
    private double rate;
    private String comment;
}
