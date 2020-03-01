package ru.krasilova.otus.spring.homework12.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework12.models.User;
import ru.krasilova.otus.spring.homework12.repositories.UserRepository;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if  (userRepository.existsByUsername(s)){
            return userRepository.findByUsername(s);
        }else{
            throw new UsernameNotFoundException(String.format("Пользователь не найден!",s));
        }
    }
}

