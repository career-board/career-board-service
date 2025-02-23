package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.dto.InterviewTypeResponse;
import net.careerboard.models.InterviewType;
import net.careerboard.repos.InterviewTypeRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterviewTypeService {
    private final InterviewTypeRepository interviewTypeRepository;

    public List<InterviewTypeResponse> getAllInterviewTypes() {
        return interviewTypeRepository.findAll().stream().map(this::mapToInterviewTypeResponse).toList();
    }

    private InterviewTypeResponse mapToInterviewTypeResponse(InterviewType interviewType) {
        return InterviewTypeResponse.builder()
                .typeId(interviewType.getTypeId())
                .name(interviewType.getName())
                .build();
    }

    public InterviewType findById(long id) throws BadRequestException {
        return interviewTypeRepository.findById(id).orElseThrow(()-> new BadRequestException("Interview type not found"));
    }
}
