package net.careerboard.models.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
public class InterviewDetailsResponse {
    private Long userId;
    private String username;
    private String description;
    private String content;
    private String status;
    private Long interviewId;
    private LocalDateTime createdAt;
    private String moderatorComment;
    private String company;
    private LocalDateTime interviewDate;
    private List<InterviewImageDto> images;
    private long typeId;
}
