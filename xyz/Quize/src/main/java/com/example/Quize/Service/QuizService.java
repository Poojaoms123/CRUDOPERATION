package com.example.Quize.Service;

import com.example.Quize.Model.Quiz;
import com.example.Quize.Model.SaveRequest.SaveQuizRequest;

import java.util.List;

public interface QuizService {
    Object saveOrUpdate(SaveQuizRequest saveQuizRequest);

    List<Quiz> getAllQuiz();
}
