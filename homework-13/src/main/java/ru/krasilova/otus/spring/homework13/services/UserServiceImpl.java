package ru.krasilova.otus.spring.homework13.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.krasilova.otus.spring.homework13.repositories.UserRepository;


@Service
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if (userRepository.existsByUsername(s)) {
            return userRepository.findByUsername(s);
        } else {
            throw new UsernameNotFoundException(String.format("Пользователь {0} не найден!", s));
        }
    }
}

