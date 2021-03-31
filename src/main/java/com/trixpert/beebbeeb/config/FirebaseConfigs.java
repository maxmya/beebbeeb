package com.trixpert.beebbeeb.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfigs {

    Logger logger = LogManager.getLogger(FirebaseConfigs.class);

    private final ResourceLoader resourceLoader;

    public FirebaseConfigs(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public FirebaseApp initFirebaseApp() throws IOException {

        Resource resource = resourceLoader.getResource("classpath:beebbeeb-admin-key.json");
        InputStream inputStream = resource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(inputStream))
                .build();

        FirebaseApp firebaseApp = FirebaseApp.initializeApp(options);
        logger.debug("Firebase app initialized");
        return firebaseApp;
    }
}
