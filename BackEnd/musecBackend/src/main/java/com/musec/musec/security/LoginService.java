package com.musec.musec.security;

import com.musec.musec.data.UserEntity;
import com.musec.musec.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Qualifier("LoginService")
public class LoginService implements UserDetailsService {
    private final UserRepository userRepo;

    public LoginService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userOrNull = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("No user with this username found."));
        return userEntityToUserDetailsConvertor(userOrNull);
    }

    private UserDetails userEntityToUserDetailsConvertor(UserEntity user){
        List<GrantedAuthority> roles = user
                .getRoles()
                .stream()
                .map(r -> new SimpleGrantedAuthority("ROLE_" + r.getRoleName()))
                .collect(Collectors.toList());
        return new User(user.getUsername(), user.getPassword(), roles);
    }
}
