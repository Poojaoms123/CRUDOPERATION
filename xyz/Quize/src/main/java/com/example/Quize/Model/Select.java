package com.example.Quize.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Quize_Select")
public class Select {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "select_id")
    private Long selectId;

    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quiz_id")
    private Long quizId;
}

