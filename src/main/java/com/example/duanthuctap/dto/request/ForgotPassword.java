package com.example.duanthuctap.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ForgotPassword {

    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Địa chỉ email không hợp lệ")
    private String email;

    @NotBlank(message = "Vui lòng điền password")
    private String password;

    @NotBlank(message = "Vui lòng nhập lại password")
    private String enterPassword;

    @NotBlank(message = "Không được để trống confirmCode")
    private String confirmCode;

}
