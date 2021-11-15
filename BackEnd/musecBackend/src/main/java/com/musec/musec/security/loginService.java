package com.musec.musec.security;

import com.musec.musec.data.userEntity;
import com.musec.musec.repositories.userRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class loginService implements UserDetailsService, AuthenticationSuccessHandler {
    private final userRepository userRepo;

    public loginService(userRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        userEntity userOrNull = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with this username found."));
        return userEntityToUserDetailsConvertor(userOrNull);
    }

    private UserDetails userEntityToUserDetailsConvertor(userEntity user){
        List<GrantedAuthority> roles = user
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getName()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), roles);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setStatus(200);
    }
}
