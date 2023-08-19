package com.example.reborn.repository;

import com.example.reborn.type.entity.SearchCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchCountRepository extends JpaRepository<SearchCount,Long> {


    List<SearchCount> findTop5ByOrderByCountDesc();

    List<SearchCount> findAllByKeywordContaining(String keyword);

    boolean existsByKeyword(String keyword);

    Optional<SearchCount> findByKeyword(String keyword);
}
