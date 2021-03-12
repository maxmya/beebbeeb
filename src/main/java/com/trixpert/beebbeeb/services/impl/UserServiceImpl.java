package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.entites.RolesEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.mappers.UserMapper;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.request.RegistrationRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.to.UserDTO;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {


    private final Logger logger = LoggerFactory.getLogger("UserService");

    private final UserRepository userRepository;
    private final ReporterService reporterService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           ReporterService reporterService,
                           PasswordEncoder passwordEncoder,
                           UserMapper userMapper) {

        this.userRepository = userRepository;
        this.reporterService = reporterService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        try {
            Optional<UserEntity> userEntity;
            if (username.contains("@")) {
                userEntity = userRepository.findByEmail(username);
            } else {
                userEntity = userRepository.findByPhone(username);
            }
            if (!userEntity.isPresent()) throw new NotFoundException("username not found !");
            return userMapper.convertToDTO(userEntity.get());
        } catch (Exception e) {
            reporterService.reportError(e);
            return null;
        }
    }

    @Override
    public ResponseWrapper<UserEntity> updateUser(UserDTO userDTO) {
        try {
            Optional<UserEntity> optionalUserRecord = userRepository.findById(userDTO.getId());
            if (!optionalUserRecord.isPresent()) {
                throw new NotFoundException(" User Entity not found");
            }

            UserEntity userRecord = optionalUserRecord.get();
            if (userDTO.getName() != null) {
                userRecord.setName(userDTO.getName());
            }

            if (userDTO.getEmail() != null) {
                userRecord.setEmail(userDTO.getEmail());
            }
            if (userDTO.getPhone() != null) {
                userRecord.setPhone(userDTO.getPhone());
            }
            userRepository.save(userRecord);
            return reporterService.reportSuccess(userRecord);
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

    @Override
    public ResponseWrapper<UserEntity> registerUser(String username,
                                                    RolesEntity role,
                                                    RegistrationRequest registrationRequest,
                                                    boolean isPhone) {

        if (isPhone && userRepository.existsByPhone(username)) {
            logger.error("attempt to register existed phone ".concat(registrationRequest.getPhone()));
            return new ResponseWrapper<>(false, "phone already registered", HttpStatus.BAD_REQUEST, null);
        } else if (userRepository.existsByEmail(username)) {
            logger.error("attempt to register existed email ".concat(registrationRequest.getEmail()));
            return new ResponseWrapper<>(false, "email already registered", HttpStatus.BAD_REQUEST, null);
        }

        try {
            UserEntity userEntityRecord = UserEntity.builder()
                    .phone(registrationRequest.getPhone())
                    .name(registrationRequest.getName())
                    .email(registrationRequest.getEmail())
                    .roles(Collections.singletonList(role))
                    .phoneFlag(isPhone)
                    .password(passwordEncoder.encode(registrationRequest.getPassword()))
                    .active(registrationRequest.isActive())
                    .build();

            UserEntity savedEntity = userRepository.save(userEntityRecord);

            return reporterService.reportSuccess(savedEntity);

        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
