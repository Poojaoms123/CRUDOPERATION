package com.example.Quize.Controller;

import com.example.Quize.Model.Question;
import com.example.Quize.Model.Quiz;
import com.example.Quize.Model.Response.EntityResponse;
import com.example.Quize.Model.SaveRequest.SaveQuestionRequest;
import com.example.Quize.Service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @PostMapping("/saveOrUpdateQuestion")
    public ResponseEntity<?>saveOrUpdateQuestion(@RequestBody SaveQuestionRequest saveQuestionRequest){
        try {
            return new ResponseEntity<>(new EntityResponse(questionService.saveOrUpdateQuestion(saveQuestionRequest),0), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
    @GetMapping("/getAllQuestionAndOptionByQuizId")
    public ResponseEntity<EntityResponse> getAllQuestionAndOptionByQuizId(@RequestParam Long quizId) {
        try {
            List<Question> questions ;
           return new ResponseEntity<>(new EntityResponse(questionService.getAllQuestionAndOptionByQuizId(quizId),0),HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(),-1),HttpStatus.OK);
        }
    }
}
