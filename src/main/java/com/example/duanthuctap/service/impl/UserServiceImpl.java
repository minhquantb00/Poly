package com.example.duanthuctap.service.impl;

import com.example.duanthuctap.dto.request.ForgotPassword;
import com.example.duanthuctap.dto.request.LoginRequest;
import com.example.duanthuctap.dto.request.RegisterRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.TokenResponse;
import com.example.duanthuctap.entity.Account;
import com.example.duanthuctap.entity.Decentralization;
import com.example.duanthuctap.enums.VaiTroEnums;
import com.example.duanthuctap.jwt.JwtService;
import com.example.duanthuctap.model.UserDetailsCustom;
import com.example.duanthuctap.repository.AccountRepository;
import com.example.duanthuctap.repository.DecentralizationRepository;
import com.example.duanthuctap.service.UserService;
import com.example.duanthuctap.until.CodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private DecentralizationRepository decentralizationRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JavaMailSender javaMailSender;

    private Map<String, String> codeMap = new HashMap<>();

    /**
     * Login vào software
     **/
    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword()));
        Optional<Account> findByUsername = accountRepository.findByUserName(loginRequest.getUsername());
        if (findByUsername.isPresent()) {
            Account refreshToken = updateToken(loginRequest.getUsername());
            String jwtToken = jwtService.generateToken(new UserDetailsCustom(findByUsername.get()));
            return TokenResponse
                    .builder()
                    .accessToken(jwtToken)
                    .token(refreshToken.getResetPasswordToken())
                    .role(findByUsername.get().getDecentralization().getAuthorityName().name())
                    .message("Login Thành Công")
                    .build();
        } else {
            return TokenResponse.builder()
                    .message("Sai username hoặc password")
                    .build();
        }
    }

    /**
     * Register account
     **/
    @Override
    public MessageResponse register(RegisterRequest registerRequest) {
        Optional<Account> optionalAccount = accountRepository.findByUserName(registerRequest.getUsername());

        if (optionalAccount.isPresent()) {
            return MessageResponse.builder().message("Tài khoản đã tồn tại").build();
        }

        Optional<Decentralization> quyenHan = decentralizationRepository.findByAuthorityName(VaiTroEnums.USER);

        if (!checkCode(registerRequest.getConfirmCode(), registerRequest.getEmail())) {
            return MessageResponse.builder().message("Mã không khớp").build();
        }

        Account account = Account
                .builder()
                .userName(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .decentralization(quyenHan.get())
                .confirmCode(registerRequest.getConfirmCode())
                .build();
        try {
            accountRepository.save(account);
            return MessageResponse.builder().message("Registered Successfully").build();
        } catch (Exception e) {
            return MessageResponse.builder().message("Lỗi trong quá trình đăng ký").build();
        }
    }

    /**
     * Quên mật khẩu
     **/
    @Override
    public MessageResponse forgotPassword(ForgotPassword forgotPassword) {
        Optional<Account> optionalAccount = accountRepository.findByEmail(forgotPassword.getEmail());
        if (optionalAccount.isEmpty()) {
            return MessageResponse.builder().message("Email không tồn tại").build();
        }
        optionalAccount.get().setPassword(forgotPassword.getPassword());
        if (!forgotPassword.getEnterPassword().equals(forgotPassword.getPassword())) {
            return MessageResponse.builder().message("Mật khẩu không khớp").build();
        }
        if (!checkCode(forgotPassword.getConfirmCode(), forgotPassword.getEmail())) {
            return MessageResponse.builder().message("Mã không khớp").build();
        }
        optionalAccount.get().setUpdateAt(LocalDate.now());
        optionalAccount.get().setPassword(passwordEncoder.encode(forgotPassword.getPassword()));
        accountRepository.save(optionalAccount.get());
        return MessageResponse.builder().message("Thay đổi mật khẩu thành công").build();
    }

    /**
     * Khi đăng nhập vào gen ra token (token đó dùng để refresh lại token khi hết hạn)
     **/
    @Override
    public Account updateToken(String username) {
        Optional<Account> findByUsername = accountRepository.findByUserName(username);
        findByUsername.get().setResetPasswordToken(UUID.randomUUID().toString());
        findByUsername.get().setResetPasswordTokenExpiry(LocalDate.from(LocalDateTime.now().plusMinutes(600000)));
        return accountRepository.save(findByUsername.get());
    }

    public Optional<Account> findByToken(String token) {
        return accountRepository.findByResetPasswordToken(token);
    }

    public Account verifyExpiration(Account resetPasswordToken) {
        if (resetPasswordToken.getResetPasswordTokenExpiry().compareTo(ChronoLocalDate.from(Instant.now())) < 0) {
            accountRepository.delete(resetPasswordToken);
            throw new RuntimeException(resetPasswordToken.getResetPasswordToken() + " Refresh token was expired. Please make a new signin request");
        }
        return resetPasswordToken;
    }

    /**
     * Check mã xem có đúng mã không
     **/
    public boolean checkCode(String code, String email) {
        return codeMap.containsKey(code) && codeMap.get(code).equals(email);
    }

    /**
     * Send mail khi đăng ký tài khoản
     **/
    @Override
    public MessageResponse sendConfirmEmailRegister(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Xác nhận đăng ký tài khoản");
        String confirmationCode = confirmationCodeRegister();
        simpleMailMessage.setText("Mã xác nhận của bạn là   " + confirmationCode);
        javaMailSender.send(simpleMailMessage);
        codeMap.put(confirmationCode, email);
        return MessageResponse.builder().message("Send mã thành công").build();
    }

    /**
     * Send mail khi quên mật khẩu
     **/
    @Override
    public MessageResponse sendConfirmEmailForgotPassWord(String email) {
        String confirmationCode = confirmationCodeForgotPassWord();
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(email);
        simpleMailMessage.setSubject("Mã xác nhận quên mật khẩu của bạn là:");
        simpleMailMessage.setText("Mã xác nhận của bạn là   " + confirmationCode);
        javaMailSender.send(simpleMailMessage);
        codeMap.put(confirmationCode, email);
        return MessageResponse.builder().message("Send mã thành công").build();
    }

    /**
     * Gen code khi đăng ký account gồm 6 số
     **/
    @Override
    public String confirmationCodeRegister() {
        return CodeGenerator.generateRandomCode(6);
    }

    /**
    * Gen code khi quên mật khẩu gồm 15 số
     **/
    @Override
    public String confirmationCodeForgotPassWord() {
        return CodeGenerator.generateRandomCode(15);
    }
}
