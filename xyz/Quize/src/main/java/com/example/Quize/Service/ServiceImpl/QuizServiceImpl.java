package com.example.Quize.Service.ServiceImpl;

import com.example.Quize.Model.Quiz;
import com.example.Quize.Model.SaveRequest.SaveQuizRequest;
import com.example.Quize.Repository.QuizRepository;
import com.example.Quize.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizServiceImpl implements QuizService {
    @Autowired
    private QuizRepository quizRepository;
    @Override
    public Object saveOrUpdate(SaveQuizRequest saveQuizRequest) {
        if (quizRepository.existsById(saveQuizRequest.getQuizId())){
            Quiz quiz = quizRepository.findById(saveQuizRequest.getQuizId()).get();
            quiz.setQuizName(saveQuizRequest.getQuizName());
            quiz.setIsDeleted(false);
            quiz.setIsActive(true);
            quizRepository.save(quiz);
            return "Updated Sucessfully";
        }else {
            Quiz quiz = new Quiz();
            quiz.setQuizName(saveQuizRequest.getQuizName());
            quiz.setIsDeleted(false);
            quiz.setIsActive(true);
            quizRepository.save(quiz);
            return "Saved Sucessfully";
        }
    }

    @Override
    public List<Quiz> getAllQuiz() {
        List<Quiz>quiz =quizRepository.getAllQuiz();
        return quiz;
    }
}
