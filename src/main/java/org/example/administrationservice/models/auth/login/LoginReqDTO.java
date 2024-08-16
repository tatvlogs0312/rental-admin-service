package org.example.administrationservice.models.auth.login;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginReqDTO {
    @NotBlank(message = "tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "mật khẩu không được để trống")
    private String password;
}
