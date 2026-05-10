package br.com.mvp.educagames.controller;

import br.com.mvp.educagames.dto.UpdateUserRequest;
import br.com.mvp.educagames.dto.UserResponse;
import br.com.mvp.educagames.security.UserPrincipal;
import br.com.mvp.educagames.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse me(@AuthenticationPrincipal UserPrincipal principal) {
        return userService.getProfile(principal.getId());
    }

    @PutMapping("/me")
    public UserResponse updateMe(@AuthenticationPrincipal UserPrincipal principal,
                                 @Valid @RequestBody UpdateUserRequest request) {
        return userService.updateProfile(principal.getId(), request);
    }
}
