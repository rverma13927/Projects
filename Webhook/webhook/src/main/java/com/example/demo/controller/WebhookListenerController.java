package com.example.demo.controller;

import com.example.demo.payload.WebhookPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("webhook")
public class WebhookListenerController {

    @PostMapping
    public ResponseEntity<String> receiveWebhook(@RequestBody WebhookPayload webhookPayload){
        // Log the payload for debugging
        System.out.println("Received Webhook Payload: ");
        System.out.println("Event: " + webhookPayload.getEvent());
        System.out.println("Data: " + webhookPayload.getData());

        // Perform any processing you need with the payload
        return new ResponseEntity<>("Webhook received successfully", HttpStatus.OK);

    }
}
