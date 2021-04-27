package com.trixpert.beebbeeb.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileScoreResponse {
    private long customerId;
    private int score;
    private int totalScore;
}
