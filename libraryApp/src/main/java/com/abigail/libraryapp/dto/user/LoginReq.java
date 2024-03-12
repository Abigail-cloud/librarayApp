package com.abigail.libraryapp.dto.user;

import lombok.Data;

@Data
public class LoginReq {
    private String email;
    private String password;
}
