package com.example.jwtdemo.security;

import com.example.jwtdemo.entity.User;
import com.example.jwtdemo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        try {

            System.out.println("Searching username = " + username);

            Optional<User> optionalUser = userRepository.findByUsername(username);

            System.out.println("Repository result = " + optionalUser);

            User user = optionalUser.orElseThrow(() ->
                    new UsernameNotFoundException("User Not Found"));

            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_" + user.getRole())
                    ));

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}