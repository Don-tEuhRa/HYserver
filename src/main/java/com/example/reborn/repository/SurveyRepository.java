package com.example.reborn.repository;

import com.example.reborn.type.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
