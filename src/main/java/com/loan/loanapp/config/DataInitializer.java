package com.loan.loanapp.config;

import com.loan.loanapp.entity.Role;
import com.loan.loanapp.entity.User;
import com.loan.loanapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        if (!userRepository.existsByEmail("admin@mail.com")) {

            User admin = User.builder()
                    .fullName("System Admin")
                    .email("admin@mail.com")
                    .phone("08111111111")
                    .password(passwordEncoder.encode("admin123"))
                    .role(Role.ADMIN)
                    .build();

            userRepository.save(admin);

            System.out.println("Default admin created.");
        }
    }
}