package net.careerboard.services;

import net.careerboard.models.Interview;
import net.careerboard.models.User;
import net.careerboard.models.dto.InterviewRequest;
import net.careerboard.repos.InterviewRepository;
import net.careerboard.repos.UserRepo;
import org.apache.coyote.BadRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class InterviewServiceTest {
    @Mock
    private InterviewRepository interviewRepository;

    @InjectMocks
    private InterviewService interviewService;

    @Mock
    private UserService userService;

    @Mock
    private UserRepo userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createInterview() throws BadRequestException {
        // Arrange
        User user = new User();
        user.setUserId(1L);
        user.setUsername("j.doe");

        Interview interview = new Interview();
        interview.setUser(user);
        InterviewRequest interviewRequest = new InterviewRequest();
        interviewRequest.setUserId(1L);
        interviewRequest.setImageNames(Arrays.asList("image1.jpg", "image2.jpg"));
        interviewRequest.setDescription("Interview title");
        interviewRequest.setContent("Interview content");
        interviewRequest.setStatus("PUBLISHED");
        when(interviewRepository.save(any(Interview.class))).thenReturn(interview);

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(user));
        when(userService.findById(any(Long.class))).thenReturn(Optional.of(user));


        // Act
        Interview createdInterview = interviewService.createInterview(interviewRequest);

        // Assert
        assertEquals(interview, createdInterview);
        verify(interviewRepository, times(1)).save(any(Interview.class));
    }

//    @Test
//    void findAllPosts() {
//        List<Interview> interviews = Arrays.asList(new Interview(), new Interview());
//        when(postRepository.findAll()).thenReturn(interviews);
//
//        List<Interview> foundPosts = interviewService.findAllPosts();
//
//        assertEquals(interviews, foundPosts);
//        verify(postRepository, times(1)).findAll();
//    }

    @Test
    void findInterviewsByUserId() {
        Long userId = 1L;
        List<Interview> interviews = Arrays.asList(new Interview(), new Interview());
        when(interviewRepository.findByUserUserId(userId)).thenReturn(interviews);

        List<Interview> foundInterviews = interviewService.findInterviewsByUserId(userId);

        assertEquals(interviews, foundInterviews);
        verify(interviewRepository, times(1)).findByUserUserId(userId);
    }

//    @Test
//    void findById() throws BadRequestException {
//        Long postId = 1L;
//        Interview post = new Interview();
//        post.setPostId(postId);
//        post.setTitle("Interview title");
//        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
//
//        InterviewResponse foundPost = interviewService.findById(postId);
//
//        assertEquals(post.getPostId(), foundPost.getPostId());
//        verify(postRepository, times(1)).findById(postId);
//    }
}