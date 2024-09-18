package com.sadatscode.securityservice.service;

import com.sadatscode.securityservice.cache.RedisVerificationService;
import com.sadatscode.securityservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {
    @Value("${spring.mail.username}")
    private String from;
    private final JavaMailSender mailSender;
    private final RedisVerificationService redisVerificationService;

    public void sendEmail(String email) {
        String verificationCode = createVerificationCode();
        String subject = "Şifrə dəyişikliyini təsdiqləmə kodunuz";
        String text = "təsdiqləmə kodu: " + verificationCode;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setSubject(subject);
        message.setText(text);
        message.setTo(email);
        mailSender.send(message);
        redisVerificationService.storeEmailVerificationCode(email,verificationCode);
    }

    public String createVerificationCode() {
        Random random = new Random();
        int verificationCode = 1000 + random.nextInt(9000);
        return String.valueOf(verificationCode);
    }

}
