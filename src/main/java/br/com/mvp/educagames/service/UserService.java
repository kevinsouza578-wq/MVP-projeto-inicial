package br.com.mvp.educagames.service;

import br.com.mvp.educagames.dto.UpdateUserRequest;
import br.com.mvp.educagames.dto.UserResponse;
import br.com.mvp.educagames.entity.User;
import br.com.mvp.educagames.exception.ConflictException;
import br.com.mvp.educagames.exception.ResourceNotFoundException;
import br.com.mvp.educagames.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LevelService levelService;

    public UserService(UserRepository userRepository, LevelService levelService) {
        this.userRepository = userRepository;
        this.levelService = levelService;
    }

    @Transactional(readOnly = true)
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario nao encontrado."));
    }

    @Transactional(readOnly = true)
    public UserResponse getProfile(Long userId) {
        return toResponse(getById(userId));
    }

    @Transactional
    public UserResponse updateProfile(Long userId, UpdateUserRequest request) {
        User user = getById(userId);

        if (hasText(request.name())) {
            user.setName(request.name().trim());
        }

        if (hasText(request.username())) {
            String normalizedUsername = request.username().trim();
            if (!normalizedUsername.equals(user.getUsername()) && userRepository.existsByUsername(normalizedUsername)) {
                throw new ConflictException("Username ja esta em uso.");
            }
            user.setUsername(normalizedUsername);
        }

        if (hasText(request.email())) {
            String normalizedEmail = request.email().trim().toLowerCase();
            if (!normalizedEmail.equals(user.getEmail()) && userRepository.existsByEmail(normalizedEmail)) {
                throw new ConflictException("E-mail ja esta em uso.");
            }
            user.setEmail(normalizedEmail);
        }

        if (request.photoUrl() != null) {
            user.setPhotoUrl(request.photoUrl().isBlank() ? null : request.photoUrl().trim());
        }

        return toResponse(userRepository.save(user));
    }

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getUsername(),
                user.getEmail(),
                user.getPhotoUrl(),
                user.getTotalScore(),
                levelService.calculateLevel(user.getTotalScore()),
                user.getCreatedAt()
        );
    }

    private boolean hasText(String value) {
        return value != null && !value.isBlank();
    }
}
