package com.georgeCsd.quizapp.restController;

import com.georgeCsd.quizapp.model.Question;
import com.georgeCsd.quizapp.model.QuestionWrapper;
import com.georgeCsd.quizapp.model.Response;
import com.georgeCsd.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {
    @Autowired
    QuizService quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numOfQuestions, @RequestParam String quizTitle) {
        return quizService.createQuiz(category, numOfQuestions, quizTitle);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable("id") Integer quizId) {
        return quizService.getQuizQuestions(quizId);
    }

    @PostMapping("submit/{quizId}")
    public ResponseEntity<String> submitQuiz(@PathVariable("quizId") Integer quizId, @RequestBody List<Response> responses) {
        return quizService.calculateResult(quizId, responses);
    }

    @PutMapping("update/{quizId}/{questionId}")
    public ResponseEntity<String> updateQuizQuestion(@PathVariable("quizId") Integer quizId, @PathVariable("questionId") Integer questionId, @RequestParam(required = false) String option1, @RequestParam(required = false) String option2, @RequestParam(required = false) String option3, @RequestParam(required = false) String option4) {
        return quizService.updateQuizQuestion(quizId, questionId, option1, option2, option3, option4);
    }
}
