package com.example.Quize.Service.ServiceImpl;

import com.example.Quize.Model.Question;
import com.example.Quize.Model.SaveRequest.SaveQuestionRequest;
import com.example.Quize.Repository.QuestionRepository;
import com.example.Quize.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Object saveOrUpdateQuestion(SaveQuestionRequest saveQuestionRequest) {
        if (questionRepository.existsById(saveQuestionRequest.getQuestionId())) {
            Question question = questionRepository.findById(saveQuestionRequest.getQuestionId()).get();
            question.setQuestionName(saveQuestionRequest.getQuestionName());
            question.setCorrectAns(saveQuestionRequest.getCorrectAns());
            question.setQuizId(saveQuestionRequest.getQuizId());
            questionRepository.save(question);
            return "Updated successfully";
        } else {
            Question question = new Question();
            question.setQuestionName(saveQuestionRequest.getQuestionName());
            question.setCorrectAns(saveQuestionRequest.getCorrectAns());
            question.setQuizId(saveQuestionRequest.getQuizId());
            questionRepository.save(question);
            return "Saved successfully";
        }
    }

    @Override
    public Object getAllQuestionAndOptionByQuizId(Long quizId) {
            return questionRepository.findById(quizId);
        }

}
