package com.trixpert.beebbeeb.services.impl;

import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.trixpert.beebbeeb.data.entites.NotificationEntity;
import com.trixpert.beebbeeb.data.entites.UserEntity;
import com.trixpert.beebbeeb.data.repositories.NotificationRepository;
import com.trixpert.beebbeeb.data.repositories.UserRepository;
import com.trixpert.beebbeeb.data.request.FCMRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.exception.NotFoundException;
import com.trixpert.beebbeeb.services.FCMService;
import com.trixpert.beebbeeb.services.ReporterService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FCMServiceImpl implements FCMService {

    private final FirebaseApp firebaseApp;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    private final ReporterService reporterService;

    public FCMServiceImpl(FirebaseApp firebaseApp,
                          NotificationRepository notificationRepository,
                          UserRepository userRepository,
                          ReporterService reporterService) {

        this.firebaseApp = firebaseApp;
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
        this.reporterService = reporterService;
    }

    @Override
    public ResponseWrapper<Boolean> sendNotification(FCMRequest request) {
        try {
            Message toSendMessage = Message.builder()
                    .putData("message", request.getMessage())
                    .putData("title", request.getTitle())
                    .putData("channel", request.getChannel())
                    .setTopic(request.getUserId() + "")
                    .build();

            String response = FirebaseMessaging.getInstance(firebaseApp).send(toSendMessage);

            Optional<UserEntity> userOptional = userRepository.findById(request.getUserId());

            if (!userOptional.isPresent()) {
                throw new NotFoundException("User Not Found !");
            }

            NotificationEntity notification = NotificationEntity
                    .builder()
                    .payload(request.getMessage())
                    .title(request.getTitle())
                    .user(userOptional.get())
                    .fcmResponse(response)
                    .build();

            notificationRepository.save(notification);
            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }
}
