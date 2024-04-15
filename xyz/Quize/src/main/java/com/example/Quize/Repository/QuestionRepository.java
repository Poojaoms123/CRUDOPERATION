package com.example.Quize.Repository;

import com.example.Quize.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    Object findByQuizId(Long quizId);
}
