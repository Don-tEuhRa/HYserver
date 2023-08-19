package com.example.reborn.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {



    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        Resource serviceAccountResource = resourceLoader.getResource("classpath:serviceAccount.json");
        InputStream serviceAccountStream = serviceAccountResource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccountStream))
                .build();

        return FirebaseApp.initializeApp(options);
    }

    @Bean
    public FirebaseAuth initFirebaseAuth(FirebaseApp firebaseApps) {
        return FirebaseAuth.getInstance(firebaseApps);
    }

    @Bean
    public FirebaseMessaging initFirebaseMessaging(FirebaseApp firebaseApps) {
        return FirebaseMessaging.getInstance(firebaseApps);
    }




}
