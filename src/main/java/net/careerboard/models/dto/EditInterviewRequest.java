package net.careerboard.models.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditInterviewRequest {
    private Long userId;
    private String description;
    private String details;
    private String status;
    private List<InterviewImageDto> images;
    private Long interviewId;
    private String editorial;
    private Long typeId;
    private String company;
    private LocalDateTime interviewDate;
}
