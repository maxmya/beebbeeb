package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.constants.AuditActions;
import com.trixpert.beebbeeb.data.entites.AuditEntity;
import com.trixpert.beebbeeb.data.repositories.AuditRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReporterServiceImpl implements ReporterService {

    private final AuditRepository auditRepository;
    private final UserRepository userRepository;

    public ReporterServiceImpl(AuditRepository auditRepository, UserRepository userRepository) {
        this.auditRepository = auditRepository;
        this.userRepository = userRepository;
    }

    public String stackTraceToString(Throwable e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public <T> ResponseWrapper<T> reportError(Exception exception, T data) {

        exception.printStackTrace();

        String errorMessage = exception.getMessage() != null ? exception.getMessage() : "ERROR";

        if (exception instanceof DataIntegrityViolationException) {
            errorMessage = "Cannot modify this entity as it's used in other relations!";
        }

        AuditEntity auditEntity =
                AuditEntity.builder()
                        .user(userRepository.getOne(0l))
                        .action(AuditActions.ERROR)
                        .description(errorMessage)
                        .timestamp(LocalDateTime.now())
                        .trace(stackTraceToString(exception))
                        .build();

        auditRepository.save(auditEntity);

        return ResponseWrapper.<T>builder()
                .data(data)
                .success(false)
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(errorMessage)
                .build();
    }

    @Override
    public <T> ResponseWrapper<T> reportError(Exception exception) {
        return reportError(exception, null);
    }

    @Override
    public <T> ResponseWrapper<T> reportSuccess(T data) {
        return ResponseWrapper.<T>builder()
                .status(HttpStatus.OK)
                .data(data)
                .success(true)
                .message("success")
                .build();
    }

    @Override
    public ResponseWrapper<Boolean> reportSuccess(String message) {
        return ResponseWrapper.<Boolean>builder()
                .status(HttpStatus.OK)
                .data(true)
                .success(true)
                .message("success")
                .build();
    }

    @Override
    public ResponseWrapper<Boolean> reportSuccess() {
        return ResponseWrapper.<Boolean>builder()
                .status(HttpStatus.OK)
                .data(true)
                .success(true)
                .message("success")
                .build();
    }

}
