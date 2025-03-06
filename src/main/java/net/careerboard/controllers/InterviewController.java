package net.careerboard.controllers;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.Interview;
import net.careerboard.models.dto.EditInterviewRequest;
import net.careerboard.models.dto.InterviewRequest;
import net.careerboard.models.dto.InterviewDetailsResponse;
import net.careerboard.models.dto.InterviewResponse;
import net.careerboard.services.InterviewService;
import net.careerboard.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interviews")
public class InterviewController {
    private final InterviewService interviewService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<InterviewResponse>> getAllInterviews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewResponse> interview = interviewService.findAllInterviews(pageable);
        return ResponseEntity.ok(interview);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<InterviewDetailsResponse>> getInterviewsByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InterviewDetailsResponse> interviews = interviewService.findInterviewsByUserId(userId, pageable);
        return ResponseEntity.ok(interviews);
    }

    // Method to fetch a interview by its ID
    @GetMapping("/{interviewId}")
    public ResponseEntity<?> getInterviewById(@PathVariable Long interviewId) {
        try {

            InterviewDetailsResponse interviewDetailsResponse = interviewService.findById(interviewId);
            return ResponseEntity.ok(interviewDetailsResponse);
        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> createInterview(@RequestBody InterviewRequest request) {
        try {
            InterviewDetailsResponse savedInterview = interviewService.createInterview(request);
            System.out.println("Interview created successfully");
            return ResponseEntity.ok(savedInterview);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editInterview(@RequestBody EditInterviewRequest request) {
        try {
            InterviewDetailsResponse editInterview = interviewService.editInterview(request);
            System.out.println("Interview Edit successfully");
            return ResponseEntity.ok(editInterview);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

}
