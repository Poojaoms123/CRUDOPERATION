package com.example.Quize.Model.SaveRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveSelectRequest {
    private Long selectId;
    private Long optionId;
    private Long questionId;
    private Long userId;
    private Long quizId;
}
