package com.abigail.libraryapp.service.userservices;

import com.abigail.libraryapp.config.jwtUtils.JwtMiddleWare;
import com.abigail.libraryapp.dto.user.LoginReq;
import com.abigail.libraryapp.dto.user.SignUpReq;
import com.abigail.libraryapp.entity.user.OpRole;
import com.abigail.libraryapp.entity.user.Role;
import com.abigail.libraryapp.entity.user.UserEntity;
import com.abigail.libraryapp.exception.UserNotFoundException;
import com.abigail.libraryapp.mapper.UserMapper;
import com.abigail.libraryapp.repository.user.RoleRepository;
import com.abigail.libraryapp.repository.user.UserRepository;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private  UserRepository userRepository;
    private final UserMapper mapper;
    private AuthenticationManager authenticationManager;
    private JwtMiddleWare jwtUtils;
    public UserService(UserRepository userRepository,
                       PasswordEncoder encoder,
                       RoleRepository roleRepository,
                       UserMapper mapper,
                       JwtMiddleWare jwtUtils,
                       AuthenticationManager authenticationManager

    ){
        this.userRepository = userRepository;
        this.encoder =encoder;
        this.roleRepository = roleRepository;
        this.mapper = mapper;
        this .authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    public UserEntity registerUser(SignUpReq sign){
        if (userRepository.existsByEmail(sign.getEmail())){
            throw new UserNotFoundException("Error: Email already in use");
        }
        UserEntity user = mapper.toEntity(sign);
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        List<String> stringRoles = sign.getRole();
        List<Role> roles = new ArrayList<>();
        if (stringRoles == null) {
            Role userRole = roleRepository.findByName(OpRole.ROLE_READER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            stringRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(OpRole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(OpRole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    case "author":
                        Role authRole = roleRepository.findByName(OpRole.ROLE_AUTHOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(authRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(OpRole.ROLE_READER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        return userRepository.save(user);
    }
    public UserDetailsImpl signIn(LoginReq loginReq) {
        UserEntity user = mapper.toLoginEntity(loginReq);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails;
    }
    public ResponseCookie signOut(){
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return cookie;
    }
}

