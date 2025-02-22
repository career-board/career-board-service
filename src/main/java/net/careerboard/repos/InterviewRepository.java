package net.careerboard.repos;

import net.careerboard.models.Interview;
import net.careerboard.models.InterviewLifecycle;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
    List<Interview> findByUserUserIdOrderByCreatedAtDesc(Long userId);
    List<Interview> findByUserUserId(Long userId);
    List<Interview> findByUserUserIdAndStatus(Long userId, InterviewLifecycle status);
}
