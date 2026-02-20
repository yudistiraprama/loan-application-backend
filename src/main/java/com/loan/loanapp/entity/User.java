package com.loan.loanapp.entity;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private String password;
    private String ktpNumber;
    private String ktpImageUrl;
    private String selfieImageUrl;
    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    public User() {
    }

    public User(Long id, String fullName, String email, String phone, String password, String ktpNumber, String ktpImageUrl, String selfieImageUrl, LocalDateTime createdAt, Role role) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.ktpNumber = ktpNumber;
        this.ktpImageUrl = ktpImageUrl;
        this.selfieImageUrl = selfieImageUrl;
        this.createdAt = createdAt;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKtpNumber() {
        return ktpNumber;
    }

    public void setKtpNumber(String ktpNumber) {
        this.ktpNumber = ktpNumber;
    }

    public String getKtpImageUrl() {
        return ktpImageUrl;
    }

    public void setKtpImageUrl(String ktpImageUrl) {
        this.ktpImageUrl = ktpImageUrl;
    }

    public String getSelfieImageUrl() {
        return selfieImageUrl;
    }

    public void setSelfieImageUrl(String selfieImageUrl) {
        this.selfieImageUrl = selfieImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
