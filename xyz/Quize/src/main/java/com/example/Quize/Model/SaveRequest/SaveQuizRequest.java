package com.example.Quize.Model.SaveRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SaveQuizRequest {
    private Long quizId;
    private String quizName;
}
