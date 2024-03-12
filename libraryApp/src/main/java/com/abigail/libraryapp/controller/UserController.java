package com.abigail.libraryapp.controller;

import com.abigail.libraryapp.config.jwtUtils.JwtMiddleWare;
import com.abigail.libraryapp.dto.responsedto.MessageResponse;
import com.abigail.libraryapp.dto.responsedto.UserInfoResponse;
import com.abigail.libraryapp.dto.user.LoginReq;
import com.abigail.libraryapp.dto.user.SignUpReq;
import com.abigail.libraryapp.entity.user.UserEntity;
import com.abigail.libraryapp.service.userservices.UserDetailsImpl;
import com.abigail.libraryapp.service.userservices.UserService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path = "/api/auth")
public class UserController {
    private UserService userService;
    private JwtMiddleWare jwtUtils;
    public UserController(UserService userService, JwtMiddleWare jwtUtils){
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }
    @PostMapping("/signUp")
    public HttpEntity<UserEntity> signUp(@Valid@RequestBody SignUpReq signUpReq){
        UserEntity user = userService.registerUser(signUpReq);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, "registered");
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public HttpEntity<?> signIn(@Valid @RequestBody LoginReq loginReq) {
        UserDetailsImpl userDetails = userService.signIn(loginReq);
        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);
        List<String> roles = userDetails.getAuthorities().stream()
                .map(role->role.getAuthority())
                .collect(Collectors.toList());
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new UserInfoResponse(
                        userDetails.getId(),
                        userDetails.getEmail(),
                        userDetails.getUsername(),
                        roles
                ));
    }
    @PostMapping("/sign-out")
    public HttpEntity<?> signOut(){
        ResponseCookie cookie = userService.signOut();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("Oh! Oga! You've signed out"));
    }
}
