package net.careerboard.models.dto;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditInterviewRequest {
    private Long userId;
    private String title;
    private String content;
    private String status;
    private List<InterviewImageDto> images;
    private Long interviewId;
    private String moderatorComment;
}
