package com.example.duanthuctap.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewResponse {

    private String contentRated;

    private Integer pointEvaluation;

    private String contentSeen;

}
