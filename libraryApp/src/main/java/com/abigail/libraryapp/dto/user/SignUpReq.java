package com.abigail.libraryapp.dto.user;

import com.abigail.libraryapp.validation.password.PasswordMatches;
import com.abigail.libraryapp.validation.password.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;



@PasswordMatches(
        password = "password",
        confirmPassword = "confirmPassword",
        message = "Password and Confirm Password must be matched!"
)

@Data
@AllArgsConstructor
public class SignUpReq {
    @NotBlank
    @Size(min = 3, max = 23)
    private String userName;
    @NotBlank
    private String email;
    @StrongPassword
    private String password;

    private String confirmPassword;
    private List<String> role;
}
