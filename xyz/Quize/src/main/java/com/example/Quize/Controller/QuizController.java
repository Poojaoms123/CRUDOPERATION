package com.example.Quize.Controller;

import com.example.Quize.Model.Quiz;
import com.example.Quize.Model.Response.EntityResponse;
import com.example.Quize.Model.SaveRequest.SaveQuizRequest;
import com.example.Quize.Service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Quiz")
public class QuizController {
    @Autowired
    private QuizService quizService;

    @PostMapping("/saveOrUpdate")
    public ResponseEntity<?> saveOrUpdate(@RequestBody SaveQuizRequest saveQuizRequest) {
        try {
            return new ResponseEntity<>(new EntityResponse(quizService.saveOrUpdate(saveQuizRequest), 0), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new EntityResponse(e.getMessage(), -1), HttpStatus.OK);
        }
    }

    @GetMapping("/getAllQuiz")
    private List<Quiz> getAllQuiz(){
        return quizService.getAllQuiz();
    }


}
