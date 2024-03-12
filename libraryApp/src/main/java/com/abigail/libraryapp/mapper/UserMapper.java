package com.abigail.libraryapp.mapper;

import com.abigail.libraryapp.dto.user.LoginReq;
import com.abigail.libraryapp.dto.user.SignUpReq;
import com.abigail.libraryapp.entity.user.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(SignUpReq sign);
    SignUpReq toDto(UserEntity user);
    UserEntity toLoginEntity(LoginReq loginReq);
    LoginReq toLoginDto(UserEntity user);
}
