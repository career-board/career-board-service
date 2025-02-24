package net.careerboard.repos;

import net.careerboard.models.Interview;
import net.careerboard.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class InterviewRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private InterviewRepository interviewRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("j.doe");
        user.setCreatedAt(LocalDateTime.now());
        entityManager.persist(user);
        entityManager.flush();
    }


    @Test
    void findByUserUserId() {
        Interview interview1 = new Interview();
        interview1.setUser(user);
        interview1.setDescription("Interview 1 title");
        interview1.setDetails("Interview 1 content");
        interview1.setCreatedAt(LocalDateTime.now());
        entityManager.persist(interview1);

        Interview interview2 = new Interview();
        interview2.setUser(user);
        interview2.setDescription("Interview 2 title");
        interview2.setDetails("Interview 2 content");
        interview2.setCreatedAt(LocalDateTime.now());
        entityManager.persist(interview2);

        entityManager.flush();

        List<Interview> interviews = interviewRepository.findByUserUserId(user.getUserId());

        assertNotNull(interviews);
        assertEquals(2, interviews.size());
        assertEquals("Interview 1 content", interviews.get(0).getDetails());
        assertEquals("Interview 2 content", interviews.get(1).getDetails());
    }
}