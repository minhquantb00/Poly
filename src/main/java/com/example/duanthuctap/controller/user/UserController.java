package com.example.duanthuctap.controller.user;

import com.example.duanthuctap.dto.request.ForgotPassword;
import com.example.duanthuctap.dto.request.LoginRequest;
import com.example.duanthuctap.dto.request.RegisterRequest;
import com.example.duanthuctap.dto.response.MessageResponse;
import com.example.duanthuctap.dto.response.TokenResponse;
import com.example.duanthuctap.service.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping("login")
    public ResponseEntity<TokenResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    @PostMapping("register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
    }

    @PutMapping("forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@Valid @RequestBody ForgotPassword ForgotPassword) {
        return new ResponseEntity<>(userService.forgotPassword(ForgotPassword), HttpStatus.OK);
    }

    @PostMapping("send-email-register")
    public ResponseEntity<MessageResponse> sendConfirmEmailRegister(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.sendConfirmEmailRegister(email), HttpStatus.OK);
    }

    @PostMapping("send-email-forgot-password")
    public ResponseEntity<MessageResponse> sendConfirmEmailForgotPassWord(@RequestParam("email") String email) {
        return new ResponseEntity<>(userService.sendConfirmEmailForgotPassWord(email), HttpStatus.OK);
    }
}
