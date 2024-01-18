package com.georgeCsd.quizapp.service;

import com.georgeCsd.quizapp.repository.QuestionRepo;
import com.georgeCsd.quizapp.model.Question;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class QuestionService {
    private final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try {
            List<Question> allQuestions = questionRepo.findAll();
            return new ResponseEntity<>(allQuestions, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error while retrieving all questions", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Question> getQuestion(Integer questionId) {
        // Attempt to find the question by ID in the repository
        Question question = questionRepo.findById(questionId)
                .orElseThrow(() -> new NoSuchElementException("Question with id " + questionId + " doesn't exist"));

        // Return the question in a ResponseEntity with a 200 OK status
        return new ResponseEntity<>(question, HttpStatus.OK);
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        List<Question> questions = questionRepo.findByCategory(category);

        if (questions != null) {
            return new ResponseEntity<>(questions, HttpStatus.OK);
        } else {
            // Handle the case where no questions are found for the specified category.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<String> addQuestion(Question question) {
        // Check if questionTitle is not empty
        if (question.getQuestionTitle() == null || question.getQuestionTitle().isEmpty()) {
            return new ResponseEntity<>("Question title is required", HttpStatus.BAD_REQUEST);
        }

        // Check if a question with the same title already exists
        if (questionRepo.existsByQuestionTitle(question.getQuestionTitle())) {
            throw new IllegalStateException("Question with title '" + question.getQuestionTitle() + "' already exists");
        }

        // Save the question to the repository
        questionRepo.save(question);

        // Return a success response
        return new ResponseEntity<>("The question was added successfully", HttpStatus.CREATED);
    }

    @Transactional
    public ResponseEntity<String> updateQuestion(Integer questionId, String questionTitle, String rightAnswer) {
        Question question = questionRepo.findById(questionId).orElseThrow(() -> new IllegalStateException("Question with id " + questionId + " doesn't exist"));

        //Check if there is another question with different id and same questionTitle
        if (questionTitle != null && !questionTitle.isEmpty() && !Objects.equals(question.getQuestionTitle(), questionTitle)) {
            if (questionRepo.existsByQuestionTitle(questionTitle)) {
                throw new IllegalStateException("Question with title '" + questionTitle + "' already exists");
            }
            question.setQuestionTitle(questionTitle);
        }

        if (rightAnswer != null && !rightAnswer.isEmpty() && !Objects.equals(question.getRightAnswer(), rightAnswer)) {
            question.setRightAnswer(rightAnswer);
        }

        return new ResponseEntity<>("The question was updated successfully", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteQuestion(Integer questionId) {
        if (!questionRepo.existsById(questionId)) {
            throw new IllegalStateException("Question with id " + questionId + " does not exist");
        }

        questionRepo.deleteById(questionId);
        return new ResponseEntity<>("The question was deleted successfully", HttpStatus.OK);
    }

}
