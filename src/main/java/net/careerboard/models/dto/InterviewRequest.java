package net.careerboard.models.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewRequest {
    private Long userId;
    private String description;
    private String details;
    private String status;
    private String company;
    private LocalDateTime interviewDate;
    private List<String> imageNames;
    private String editorial;
    private long typeId;
}
