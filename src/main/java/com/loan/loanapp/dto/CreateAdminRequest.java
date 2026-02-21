package com.loan.loanapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateAdminRequest(
        @NotBlank String fullName,

        @Email
        @NotBlank String email,

        @NotBlank String phone,

        @NotBlank String password
) {}