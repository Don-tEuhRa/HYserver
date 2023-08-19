package com.example.reborn.type.entity;

import com.example.reborn.type.etc.SurveyQuestion;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "survey")
@NoArgsConstructor
@Getter
@Setter
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long surveyId;

    @Enumerated(EnumType.STRING)
    private SurveyQuestion surveyQuestion;

    @Column(nullable = false)
    private Long weight;

    @ManyToOne
    @JoinColumn(name = "review_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Review review;

    @Builder
    public Survey(SurveyQuestion surveyQuestion, Long weight, Review review){
        this.surveyQuestion = surveyQuestion;
        this.weight = weight;
        this.review = review;
    }
    public void setWeight(Long weight) {
        this.weight = weight;
    }
}
