package com.georgeCsd.quizapp.service;

import com.georgeCsd.quizapp.model.Question;
import com.georgeCsd.quizapp.model.QuestionWrapper;
import com.georgeCsd.quizapp.model.Quiz;
import com.georgeCsd.quizapp.model.Response;
import com.georgeCsd.quizapp.repository.QuizRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.georgeCsd.quizapp.repository.QuestionRepo;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QuizService {
    @Autowired
    QuizRepo quizRepo;

    @Autowired
    QuestionRepo questionRepo;

    public ResponseEntity<String> createQuiz(String category, int numOfQuestions, String quizTitle) {

        // Check if a quiz with the same title already exists
        if (quizRepo.existsByTitle(quizTitle)) {
            throw new IllegalStateException("Quiz with title '" + quizTitle + "' already exists");
        }

        // Validate category and numOfQuestions
        if (category == null || numOfQuestions <= 0) {
            return new ResponseEntity<>("Invalid category or number of questions", HttpStatus.BAD_REQUEST);
        }

        // Fetch random questions from the database
        List<Question> questionList = questionRepo.findRandomQuestionsByCategory(category, numOfQuestions);

        // Validate if enough questions were found
        if (questionList.size() < numOfQuestions) {
            return new ResponseEntity<>("Not enough questions available for the specified category", HttpStatus.BAD_REQUEST);
        }

        // Create a new quiz
        Quiz quiz = new Quiz();
        quiz.setTitle(quizTitle);
        quiz.setQuestions(questionList);

        // Save the quiz to the repository
        quizRepo.save(quiz);

        // Return a success response
        return new ResponseEntity<>("The quiz was created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer quizId) {
        Optional<Quiz> quizOptional = quizRepo.findById(quizId);

        if (quizOptional.isPresent()) {
            List<Question> questionsFromDB = quizOptional.get().getQuestions();
            List<QuestionWrapper> questionForUser = questionsFromDB.stream()
                    .map(q -> new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4()))
                    .collect(Collectors.toList());

            return new ResponseEntity<>(questionForUser, HttpStatus.OK);
        } else {
            throw new IllegalStateException("Quiz with id " + quizId + " doesn't exist");
        }
    }

    public ResponseEntity<String> calculateResult(Integer quizId, List<Response> responses) {
        Quiz quiz = quizRepo.findById(quizId)
                .orElseThrow(() -> new NoSuchElementException("Quiz with id " + quizId + " doesn't exist"));

        List<Question> questions = quiz.getQuestions();

        if (questions.size() != responses.size()) {
            return new ResponseEntity<>("Mismatch in the number of questions and responses", HttpStatus.BAD_REQUEST);
        }

        int rightAnswers = 0;

        for (int i = 0; i < responses.size(); i++) {
            String userResponse = responses.get(i).getResponse();
            String correctAnswer = questions.get(i).getRightAnswer();

            if (userResponse.equals(correctAnswer)) {
                rightAnswers++;
            }
        }

        return new ResponseEntity<>("You have " + rightAnswers + " correct answers", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateQuizQuestion(Integer quizId, Integer questionId, String option1, String option2, String option3, String option4) {
        Quiz quiz = quizRepo.findById(quizId).orElseThrow(() -> new NoSuchElementException("Quiz with id " + quizId + " doesn't exist"));

        Optional<Question> optionalQuestion = quiz.getQuestions().stream()
                .filter(question -> question.getId().equals(questionId))
                .findFirst();

        if (optionalQuestion.isPresent()) {
            Question question = optionalQuestion.get();

            if (option1 != null && option1.length() > 0 && !Objects.equals(question.getOption1(), option1)) {
                question.setOption1(option1);
            }
            if (option2 != null && option2.length() > 0 && !Objects.equals(question.getOption2(), option2)) {
                question.setOption2(option2);
            }
            if (option3 != null && option3.length() > 0 && !Objects.equals(question.getOption3(), option3)) {
                question.setOption3(option3);
            }
            if (option4 != null && option4.length() > 0 && !Objects.equals(question.getOption4(), option4)) {
                question.setOption4(option4);
            }

            return new ResponseEntity<>("The question was updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Question with id " + questionId + " not found in the quiz", HttpStatus.NOT_FOUND);
        }
    }
}
