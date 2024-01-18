package com.georgeCsd.quizapp.repository;

import com.georgeCsd.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuestionRepo extends JpaRepository<Question,Integer> {

    @Query("SELECT q FROM Question q WHERE q.questionTitle=?1")
    Optional<Question> findQuestionByTitle(String title);

    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numOfQuestions",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numOfQuestions);

    boolean existsByQuestionTitle(String questionTitle);

    List<Question> findByCategory(String category);
}
