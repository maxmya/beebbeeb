package com.trixpert.beebbeeb.data.response;


import lombok.*;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {
    private double rate;
    private String comment;
    private Date reviewDate;
    private Date commentDate;
}
