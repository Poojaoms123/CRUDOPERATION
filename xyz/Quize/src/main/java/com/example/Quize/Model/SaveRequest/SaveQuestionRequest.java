package com.example.Quize.Model.SaveRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class SaveQuestionRequest {
    private Long questionId;
    private String questionName;
    private String correctAns;
    private Long quizId;

}
