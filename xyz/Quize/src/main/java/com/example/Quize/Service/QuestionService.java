package com.example.Quize.Service;

import com.example.Quize.Model.Question;
import com.example.Quize.Model.SaveRequest.SaveQuestionRequest;

import java.util.Optional;

public interface QuestionService {
    Object saveOrUpdateQuestion(SaveQuestionRequest saveQuestionRequest);

    Object getAllQuestionAndOptionByQuizId(Long quizId);
}
