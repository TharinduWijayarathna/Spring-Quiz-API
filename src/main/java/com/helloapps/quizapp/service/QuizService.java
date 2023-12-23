package com.helloapps.quizapp.service;

import com.helloapps.quizapp.model.Question;
import com.helloapps.quizapp.model.QuestionWrapper;
import com.helloapps.quizapp.model.Quiz;
import com.helloapps.quizapp.model.Response;
import com.helloapps.quizapp.repo.QuestionRepository;
import com.helloapps.quizapp.repo.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizRepository quizRepository;

    @Autowired
    QuestionRepository questionRepository;

    public ResponseEntity<String> createQuiz(String category, int numberOfQuestions, String title) {
        try {
            List<Question> questions = questionRepository.findRandomQuestions(category, numberOfQuestions);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quizRepository.save(quiz);

            return new ResponseEntity<>("Quiz created successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Quiz not created", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();

            for (Question question : questionsFromDB) {
                QuestionWrapper questionWrapper = new QuestionWrapper(question.getId(), question.getQuestionTitle(), question.getOption1(), question.getOption2(), question.getOption3(), question.getOption4());
                questionsForUser.add(questionWrapper);
            }

            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(0), HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        try {
            Quiz quiz = quizRepository.findById(id).get();
            List<Question> questions = quiz.getQuestions();

            int score = 0;
            int iterator = 0;
            for (Response response : responses) {
                if (response.getResponse().equals(questions.get(iterator).getCorrectAnswer())) {
                    score++;
                }
                iterator++;
            }

            return new ResponseEntity<>(score, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(0, HttpStatus.OK);
    }
}
