package com.example.reborn.type.etc;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum SurveyQuestion {

    질문1(1L, "Re:Born 서비스를 통해 기부에 대한 인식이 개선되었다."),
    질문2(2L, "Re:Born 을 통한 나의 기부 과정이 투명하게 보여졌다고 느낀다."),
    질문3(3L, "Re:Born 쇼핑몰에 대해 만족한다."),
    질문4(4L, "Re:Born 어플을 다시 이용할 의향이 있다.");

    private Long value;
    private String description;

    public static List<String> getSurveyQuestion() {
        List<String> question = new ArrayList<>();
        for (SurveyQuestion surveyQuestion : SurveyQuestion.values()) {
            question.add(surveyQuestion.getDescription());
        }
        return question;
    }

    public static SurveyQuestion findByValue(Long value) {
        for (SurveyQuestion surveyQuestion : SurveyQuestion.values()) {
            if (surveyQuestion.getValue().equals(value)) {
                return surveyQuestion;
            }
        }
        return null;
    }
}
