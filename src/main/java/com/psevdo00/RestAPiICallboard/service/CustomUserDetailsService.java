package com.psevdo00.RestAPiICallboard.service;

import com.psevdo00.RestAPiICallboard.entity.UserEntity;
import com.psevdo00.RestAPiICallboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    public CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
        System.out.println("✅ CustomUserDetailsService инициализирован");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("Загрузка пользователя по username: " + username);

        UserEntity user = repository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Пользователь не найден!");
        }

        return user;
    }
}
