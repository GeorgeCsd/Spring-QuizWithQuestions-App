package com.georgeCsd.quizapp.restController;

import com.georgeCsd.quizapp.model.Question;
import com.georgeCsd.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @GetMapping("getAll")
    public ResponseEntity<List<Question>> getAllQuestions() {
        return questionService.getAllQuestions();
    }

    @GetMapping("get/{questionId}")
    public ResponseEntity<Question> getQuestion(@PathVariable("questionId") Integer questionId) {
        return questionService.getQuestion(questionId);
    }

    @GetMapping("getCategory/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable("category") String category) {
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping(path = "update/{questionId}")
    public ResponseEntity<String> updateQuestion(
            @PathVariable("questionId") Integer questionId,
            @RequestParam(required = false) String questionTitle, @RequestParam(required = false) String rightAnswer) {
        return questionService.updateQuestion(questionId, questionTitle, rightAnswer);
    }

    @DeleteMapping(path = "delete/{questionId}")
    public ResponseEntity<String> deleteQuestion(@PathVariable("questionId") Integer questionId) {
        return questionService.deleteQuestion(questionId);
    }

}
