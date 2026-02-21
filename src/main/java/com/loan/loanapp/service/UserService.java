package com.loan.loanapp.service;

import com.loan.loanapp.entity.User;
import com.loan.loanapp.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public UserService(UserRepository userRepository, FileStorageService fileStorageService) {
        this.userRepository = userRepository;
        this.fileStorageService = fileStorageService;
    }

    public String uploadKtp(Authentication auth, MultipartFile file) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        String path = fileStorageService.saveFile(file, "ktp");

        user.setKtpImageUrl(path);
        userRepository.save(user);

        return "KTP uploaded successfully";
    }

    public String uploadSelfie(Authentication auth, MultipartFile file) {

        User user = userRepository.findByEmail(auth.getName()).orElseThrow(() -> new RuntimeException("User not found"));

        String path = fileStorageService.saveFile(file, "selfie");

        user.setSelfieImageUrl(path);
        userRepository.save(user);

        return "Selfie uploaded successfully";
    }
}
