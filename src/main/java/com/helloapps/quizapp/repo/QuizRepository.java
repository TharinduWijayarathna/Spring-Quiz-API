package com.helloapps.quizapp.repo;

import com.helloapps.quizapp.modal.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Integer> {
}
