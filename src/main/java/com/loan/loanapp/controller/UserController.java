package com.loan.loanapp.controller;

import com.loan.loanapp.dto.ApiResponse;
import com.loan.loanapp.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/upload-ktp")
    public ApiResponse<String> uploadKtp(@RequestParam("file") MultipartFile file, Authentication auth) {
        return new ApiResponse<>(true, userService.uploadKtp(auth, file), null);
    }

    @PostMapping("/upload-selfie")
    public ApiResponse<String> uploadSelfie(@RequestParam("file") MultipartFile file, Authentication auth) {
        return new ApiResponse<>(true, userService.uploadSelfie(auth, file), null);
    }
}
