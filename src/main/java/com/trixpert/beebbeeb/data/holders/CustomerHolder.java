package com.trixpert.beebbeeb.data.holders;

import com.trixpert.beebbeeb.data.request.CustomerMobileRegistrationRequest;
import com.trixpert.beebbeeb.data.request.CustomerRegistrationRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class CustomerHolder {

    @Bean("customers_queue")
    public Map<String, CustomerMobileRegistrationRequest> createCustomerQueue() {
        return new ConcurrentHashMap<>();
    }
}
