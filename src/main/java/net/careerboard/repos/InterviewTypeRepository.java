package net.careerboard.repos;

import net.careerboard.models.InterviewType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InterviewTypeRepository extends JpaRepository<InterviewType, Long> {
}
