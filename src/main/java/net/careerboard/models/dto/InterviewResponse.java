package net.careerboard.models.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterviewResponse {

    private Long userId;
    private String username;
    private String title;
    private String status;
    private Long interviewId;
    private LocalDateTime createdAt;
}
