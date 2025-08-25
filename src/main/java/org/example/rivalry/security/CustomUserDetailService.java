package org.example.rivalry.security;


import org.example.rivalry.entity.UserPlayer;
import org.example.rivalry.repository.UserPlayerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service

public class CustomUserDetailService implements UserDetailsService {

    private final UserPlayerRepository userRepository;

    public CustomUserDetailService(UserPlayerRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserPlayer> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()){
            UserPlayer user = userOptional.get();
            Set<GrantedAuthority> authorities = new HashSet<>();

            if (user.getRole().toString().equals("ADMIN")) authorities.add(new SimpleGrantedAuthority("ADMIN"));
            else authorities.add(new SimpleGrantedAuthority("PLAYER"));

            return new User(user.getEmail(), user.getPassword(),authorities);
        }
        return null;
    }
}
