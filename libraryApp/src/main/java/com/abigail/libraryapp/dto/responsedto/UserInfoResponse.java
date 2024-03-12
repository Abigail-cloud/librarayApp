package com.abigail.libraryapp.dto.responsedto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class UserInfoResponse {
    private Long id;
    private String email;
    private String userName;
    private List<String> roles;
}
