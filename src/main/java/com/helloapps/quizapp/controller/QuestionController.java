package com.helloapps.quizapp.controller;

import com.helloapps.quizapp.model.Question;
import com.helloapps.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("/all")
    public ResponseEntity<List<Question>> allQuestions() {
        return questionService.allQuestions();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable Integer id) {
        return questionService.getQuestion(id);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> questionsByCategory(@PathVariable String category) {
        return questionService.questionsByCategory(category);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question) {
        return questionService.addQuestion(question);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateQuestion(@RequestBody Question question) {
        return questionService.updateQuestion(question);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteQuestion(@PathVariable Integer id) {
        return questionService.deleteQuestion(id);
    }
}
