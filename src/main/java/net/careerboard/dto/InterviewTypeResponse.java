package net.careerboard.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InterviewTypeResponse {

    private Long typeId;
    private String name;
}
