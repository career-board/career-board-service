package net.careerboard.services;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import net.careerboard.dto.UserResponse;
import net.careerboard.models.Role;
import net.careerboard.models.User;
import net.careerboard.models.dto.*;
import net.careerboard.repos.UserRepo;
import net.careerboard.security.jwt.JwtUtil;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ResDto<Object> registerUser(UserRegistrationRequest request) {
        try {
            // Check if username already exists
            if (userService.existsByUsername(request.getUsername())) {
                return new ResDto<>(Boolean.FALSE, ResDTOMessage.USERNAME_ALREADY_EXIST, request.getUsername());
            }

            // Check if email already exists
            if (userService.existsByEmail(request.getEmail())) {
                return new ResDto<>(Boolean.FALSE, ResDTOMessage.EMAIL_ALREADY_EXIST, request.getEmail());
            }

            // Create new user
            User user = User.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(request.getPassword())
                    .role(Role.USER)
                    .active(true)
                    .currentCompany(request.getCurrentCompany())
                    .build();

            validateUser(user);

            // Save user without the raw password
            UserResponse userResponse = userService.addUser(user);

            return new ResDto<>(Boolean.TRUE, ResDTOMessage.CREATED, userResponse);

        } catch (DataIntegrityViolationException e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.DATABASE_CONSTRAINT_VIOLATION, e.getMessage());
        } catch (Exception e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    public ResDto<Object> login(LoginRequest loginRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
            );

            User user = userRepo.findByUsername(loginRequest.username())
                    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtUtil.generateToken(user);
            AuthResponse authResponse = new AuthResponse(token);
            return new ResDto<>(Boolean.TRUE, ResDTOMessage.SUCCESS, authResponse);
        } catch (BadCredentialsException e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.WRONG_USERNAME_OR_PASSWORD, null);
        } catch (Exception e) {
            return new ResDto<>(Boolean.FALSE, ResDTOMessage.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void validateUser(User user) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
