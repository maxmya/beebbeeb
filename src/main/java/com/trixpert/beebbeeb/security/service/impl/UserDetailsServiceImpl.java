package com.trixpert.beebbeeb.security.service.impl;

import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.security.UserPrinciple;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.contains("@")) {
            if (userRepository.existsByEmail(username)) {
                Optional<UserEntity> userEntityOptional = userRepository.findByEmail(username);
                if (!userEntityOptional.isPresent())
                    throw new UsernameNotFoundException("no user matching the entered username");
                else
                    return new UserPrinciple(userEntityOptional.get());
            } else throw new UsernameNotFoundException("no user matching the entered username");
        } else {
            if (userRepository.existsByPhone(username)) {
                Optional<UserEntity> userEntityOptional = userRepository.findByPhone(username);
                if (!userEntityOptional.isPresent())
                    throw new UsernameNotFoundException("no user matching the entered username");
                else
                    return new UserPrinciple(userEntityOptional.get());
            } else throw new UsernameNotFoundException("no user matching the entered username");
        }
    }


}
