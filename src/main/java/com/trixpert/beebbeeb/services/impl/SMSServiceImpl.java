package com.trixpert.beebbeeb.services.impl;

import com.trixpert.beebbeeb.data.request.SmsRequest;
import com.trixpert.beebbeeb.data.response.ResponseWrapper;
import com.trixpert.beebbeeb.data.response.SmsResponse;
import com.trixpert.beebbeeb.services.ReporterService;
import com.trixpert.beebbeeb.services.SMSService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class SMSServiceImpl implements SMSService {

    @Value("${sms.sender.id}")
    private String smsSenderId;

    @Value("${sms.send.url}")
    private String smsSendUrl;

    @Value("${sms.api.key}")
    private String smsApiKey;

    private final ReporterService reporterService;

    public SMSServiceImpl(ReporterService reporterService) {
        this.reporterService = reporterService;
    }


    @Override
    public ResponseWrapper<Boolean> sendSMSMessage(String message, String phone) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            SmsRequest smsRequest = new SmsRequest();

            SmsRequest.SmsMessage smsMessage = new SmsRequest.SmsMessage();
            smsMessage.setFrom(this.smsSenderId);
            smsMessage.setText(message);

            SmsRequest.SmsMessage.Destination destination = new SmsRequest.SmsMessage.Destination();
            destination.setTo(phone);

            smsMessage.setDestinations(Collections.singletonList(destination));
            smsRequest.setMessages(Collections.singletonList(smsMessage));

            HttpHeaders headers = new HttpHeaders();

            headers.add("Authorization", "App ".concat(this.smsApiKey));

            HttpEntity<SmsRequest> request = new HttpEntity<>(smsRequest, headers);
            ResponseEntity<SmsResponse> response =
                    restTemplate.exchange(
                            this.smsSendUrl,
                            HttpMethod.POST,
                            request,
                            SmsResponse.class);

            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                throw new IllegalAccessException("Cannot Send SMS");
            }
            return reporterService.reportSuccess();
        } catch (Exception e) {
            return reporterService.reportError(e);
        }
    }

}
