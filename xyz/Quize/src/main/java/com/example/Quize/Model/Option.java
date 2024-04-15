package com.example.Quize.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Data
@Entity
@Getter
@Setter
@Table(name = "Quize_Option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "option_name")
    private String optionName;

    @Column(name = "question_id")
    private Long questionId;
}
