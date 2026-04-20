package com.infy.service;


import com.infy.entity.User;
import com.infy.enums.Role;
import com.infy.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userDAO;

    public CustomUserDetailsService(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with login: " + username));



        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .authorities(mapRolesToAuthorities(user.getRole()))
                .disabled(false)
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .build();
    }

    Collection<? extends GrantedAuthority> mapRolesToAuthorities(Role role) {
        return Collections.singleton(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }
}
