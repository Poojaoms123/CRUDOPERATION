package com.example.Quize.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Getter
@Setter
@Table(name = "Quize_Question")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "question_name")
    private String questionName;

    @Column(name = "correct_ans")
    private String correctAns;

    @Column(name = "quiz_id")
    private Long quizId;

    @CreationTimestamp
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

}
