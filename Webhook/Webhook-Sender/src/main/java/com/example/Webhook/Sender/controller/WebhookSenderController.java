package com.example.Webhook.Sender.controller;


import com.example.Webhook.Sender.service.WebhookSenderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebhookSenderController {

    private final WebhookSenderService webhookSenderService;

    public WebhookSenderController(WebhookSenderService webhookSenderService) {
        this.webhookSenderService = webhookSenderService;
    }

    @GetMapping("/send-webhook")
    public String sendWebhook() {
        webhookSenderService.sendWebhook();
        return "Webhook sent!";
    }
}