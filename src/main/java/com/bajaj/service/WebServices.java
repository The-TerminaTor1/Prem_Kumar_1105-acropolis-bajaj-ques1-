package com.bajaj.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import com.bajaj.model.SQLQueryreq;
import com.bajaj.model.WebhookGenerationInput;
import com.bajaj.model.WebhookGenerationOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WebServices {

    private static final Logger logger = LoggerFactory.getLogger(WebServices.class);
    private final RestTemplate restTemplate;

    public WebServices() {
        this.restTemplate = new RestTemplate();
    }

    public WebhookGenerationInput generatewebhook(WebhookGenerationOutput output) {
        String url = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";
        try {
            HttpEntity<WebhookGenerationOutput> entity = new HttpEntity<>(output);
            return restTemplate.postForObject(url, entity, WebhookGenerationInput.class);
        } catch (RestClientException e) {
            logger.error("Failed to generate webhook: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate webhook: " + e.getMessage(), e);
        }
    }

    public String FQuery(String webhookUrl, String accessToken, SQLQueryreq request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<SQLQueryreq> entity = new HttpEntity<>(request, headers);
            return restTemplate.postForObject(webhookUrl, entity, String.class);
        } catch (RestClientException e) {
            logger.error("Failed to submit query to {}: {}", webhookUrl, e.getMessage(), e);
            throw new RuntimeException("Failed to submit query: " + e.getMessage(), e);
        }
    }
}