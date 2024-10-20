package com.example.Webhook.Sender.service;

import com.example.Webhook.Sender.payload.WebhookPayload;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class WebhookSenderService {

    private final WebClient webclient;

    // Initialize WebClient to send requests
    public WebhookSenderService() {
        this.webclient = WebClient.create();
    }
    public void sendWebhook() {
        WebhookPayload payload = new WebhookPayload();
        payload.setEvent("user_registered");
        payload.setData("User ID: 12345");

        // Send POST request to the Webhook Listener
        webclient.post()
                .uri("http://localhost:8080/webhook") // Webhook listener's URL
                .body(Mono.just(payload), WebhookPayload.class)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(response -> {
                    System.out.println("Received response from listener: " + response);
                });
    }
}
