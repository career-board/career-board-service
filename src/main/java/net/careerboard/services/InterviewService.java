package net.careerboard.services;

import lombok.RequiredArgsConstructor;
import net.careerboard.models.*;
import net.careerboard.models.dto.EditInterviewRequest;
import net.careerboard.models.dto.InterviewDetailsResponse;
import net.careerboard.models.dto.InterviewImageDto;
import net.careerboard.models.dto.InterviewRequest;
import net.careerboard.models.dto.InterviewResponse;
import net.careerboard.repos.InterviewRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InterviewService {
    private final InterviewRepository interviewRepository;
    private final UserService userService;
    private final InterviewTypeService interviewTypeService;

    public InterviewDetailsResponse createInterview(InterviewRequest request) throws BadRequestException {
        try {
            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Interview interview = new Interview();
                interview.setUser(user);
                interview.setDescription(request.getDescription());
                interview.setDetails(request.getDetails());
                interview.setCreatedAt(LocalDateTime.now());
                interview.setStatus(InterviewLifecycle.valueOf(request.getStatus()));
                interview.setCompany(request.getCompany());
                interview.setInterviewDate(request.getInterviewDate());
                List<InterviewImage> interviewImageList = request.getImageNames().stream().map(imageName -> {
                    InterviewImage interviewImage = new InterviewImage();
                    interviewImage.setImageName(imageName);
                    interviewImage.setInterview(interview); // Set the interview reference
                    return interviewImage;
                }).toList();
                interview.setImages(interviewImageList);
                InterviewType interviewType = interviewTypeService.findById(request.getTypeId());
                interview.setInterviewType(interviewType);

                String moderatorComment = getModeratorComment(request.getEditorial(), interview);
                interview.setEditorial(moderatorComment);

                return mapToInterviewDetailsResponse(interviewRepository.save(interview));
            } else {
                throw new BadRequestException("User with ID %d not found!".formatted(request.getUserId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }

    private static String getModeratorComment(String request, Interview interview) {
        Set<String> rolesWithCommentPermission = Set.of("MODERATOR", "ADMIN");
        SecurityContext context = SecurityContextHolder.getContext();
        List<String> list = context.getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        System.out.println(list);
        if (rolesWithCommentPermission.contains(list.get(0))) {
            System.out.println(request);
            return request;
        }
        return null;
    }

    public Page<InterviewResponse> findAllInterviews(Pageable pageable) {
        Page<Interview> interviews = interviewRepository.findAll(PageRequest.of(
            pageable.getPageNumber(),
            pageable.getPageSize(),
            Sort.by(Sort.Direction.DESC, "createdAt")
        ));
        return interviews.map(InterviewService::mapToInterviewResponse);
    }

    private static InterviewResponse mapToInterviewResponse(Interview interview) {
        return InterviewResponse.builder()
                .interviewId(interview.getInterviewId())
                .userId(interview.getUser().getUserId())
                .username(interview.getUser().getUsername())
                .description(interview.getDescription())
                .status(interview.getStatus().name())
                .createdAt(interview.getCreatedAt())
                .typeId(interview.getInterviewType().getTypeId())
                .typeName(interview.getInterviewType().getName())
                .company(interview.getCompany())
                .interviewDate(interview.getInterviewDate())
                .build();
    }

    public Page<InterviewDetailsResponse> findInterviewsByUserId(Long userId, Pageable pageable) {
        Page<Interview> interviews = this.interviewRepository.findByUserUserIdOrderByCreatedAtDesc(userId, pageable);
        return interviews.map(InterviewService::mapToInterviewDetailsResponse);
    }

    public List<Interview> findPublishedInterviewsByUserId(Long userId) {
        return this.interviewRepository.findByUserUserIdAndStatus(userId, InterviewLifecycle.PUBLISHED);
    }

    public InterviewDetailsResponse findById(Long interviewId) throws BadRequestException {
        Optional<Interview> optionalInterview = this.interviewRepository.findById(interviewId);
        if (optionalInterview.isEmpty()) {
            throw new BadRequestException("Interview with ID %d not found!".formatted(interviewId));
        } else {
            Interview interview = optionalInterview.get();
            return mapToInterviewDetailsResponse(interview);
        }
    }

    private static InterviewDetailsResponse mapToInterviewDetailsResponse(Interview interview) {
        return InterviewDetailsResponse.builder()
                .interviewId(interview.getInterviewId())
                .userId(interview.getUser().getUserId())
                .username(interview.getUser().getUsername())
                .description(interview.getDescription())
                .details(interview.getDetails())
                .status(interview.getStatus().name())
                .interviewId(interview.getInterviewId())
                .createdAt(interview.getCreatedAt())
                .company(interview.getCompany())
                .interviewDate(interview.getInterviewDate())
                .editorial(interview.getEditorial())
                .images(interview.getImages().stream().map(interviewImage -> InterviewImageDto.builder()
                        .imageId(interviewImage.getImageId())
                        .imageName(interviewImage.getImageName())
                        .build()).toList())
                .typeId(interview.getInterviewType().getTypeId())
                .typeName(interview.getInterviewType().getName())
                .build();
    }

    public InterviewDetailsResponse editInterview(EditInterviewRequest request) throws BadRequestException {
        try {
            Optional<User> userOptional = userService.findById(request.getUserId());
            if (userOptional.isPresent()) {
                User user = userOptional.get();
                Interview interview = new Interview();
                interview.setInterviewId(request.getInterviewId());
                interview.setUser(user);
                interview.setDescription(request.getDescription());
                interview.setDetails(request.getDetails());
                interview.setCreatedAt(LocalDateTime.now());
                interview.setStatus(InterviewLifecycle.valueOf(request.getStatus()));
                interview.setCompany(request.getCompany());
                interview.setInterviewDate(request.getInterviewDate());
                String moderatorComment = getModeratorComment(request.getEditorial(), interview);
                interview.setEditorial(moderatorComment);
                List<InterviewImage> interviewImageList = request.getImages().stream().map(image -> {
                    InterviewImage interviewImage = new InterviewImage();
                    interviewImage.setImageName(image.getImageName());
                    if (image.getImageId() == 0) {
                        interviewImage.setImageId(interviewImage.getImageId());
                    }
                    interviewImage.setInterview(interview); // Set the interview reference
                    return interviewImage;
                }).toList();
                interview.setImages(interviewImageList);
                InterviewType interviewType = interviewTypeService.findById(request.getTypeId());
                interview.setInterviewType(interviewType);

                return mapToInterviewDetailsResponse(interviewRepository.save(interview));
            } else {
                throw new BadRequestException("User with ID %d not found!".formatted(request.getUserId()));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new BadRequestException(e.getMessage());
        }
    }
}
