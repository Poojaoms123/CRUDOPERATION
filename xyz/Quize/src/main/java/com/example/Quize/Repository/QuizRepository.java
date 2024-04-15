package com.example.Quize.Repository;

import com.example.Quize.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz,Long> {
    @Query(value = "select * From Quize_Quize where is_deleted = false ",nativeQuery = true)
    List<Quiz> getAllQuiz();
}
