package com.abigail.libraryapp.config.jwtUtils;

import com.abigail.libraryapp.service.userservices.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * – get JWT from the Authorization header (by removing Bearer prefix)
 * – if the request has JWT, validate it, parse email from it
 * – from email, get UserDetails to create an Authentication object
 * – set the current UserDetails in SecurityContext using setAuthentication(authentication) method.
 */
public class AuthTokenFilter extends OncePerRequestFilter {
    @Autowired
    private JwtMiddleWare jwtUtils;
    @Autowired
    UserDetailsServiceImpl userDetailsService;

    private final static Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        if ("/api/auth".equals(path)){
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String jwt = parseJwt(request);
            if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                String user = jwtUtils.getUserFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(user);

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,
                                null,
                                userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request) {
        return jwtUtils.getJwtFromCookies(request);
    }
}
