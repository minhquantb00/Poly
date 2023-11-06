package com.example.duanthuctap.dto.request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewRequest {

    private String contentRated;

    private Integer pointEvaluation;

    private String contentSeen;
}
