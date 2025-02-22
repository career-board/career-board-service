package net.careerboard.models.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewRequest {
    private Long userId;
    private String description;
    private String content;
    private String status;
    private List<String> imageNames;
}
