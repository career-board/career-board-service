package net.careerboard.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import net.careerboard.dto.InterviewTypeResponse;
import net.careerboard.services.InterviewTypeService;

@RestController
@RequestMapping("/api/interview-types")
@RequiredArgsConstructor
public class InterviewTypeController {
    private final InterviewTypeService interviewTypeService;

    @GetMapping
    public ResponseEntity<List<InterviewTypeResponse>> getAllInterviewTypes() {
        return ResponseEntity.ok(interviewTypeService.getAllInterviewTypes());
    }
}
