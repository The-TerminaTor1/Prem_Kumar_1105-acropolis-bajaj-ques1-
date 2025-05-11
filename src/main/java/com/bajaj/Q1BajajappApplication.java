package com.bajaj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bajaj.model.SQLQueryreq;
import com.bajaj.model.WebhookGenerationInput;
import com.bajaj.model.WebhookGenerationOutput;
import com.bajaj.service.WebServices;

@SpringBootApplication
public class Q1BajajappApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(Q1BajajappApplication.class);
    private final WebServices services;

    @Autowired
    public Q1BajajappApplication(WebServices services) {
        super();
        this.services = services;
    }

    public static void main(String[] args) {
        SpringApplication.run(Q1BajajappApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            WebhookGenerationOutput request = new WebhookGenerationOutput(
                "John Doe",
                "REG12347",
                "john@example.com"
            );

            WebhookGenerationInput response = services.generatewebhook(request);
            if (response == null || response.getWebhook() == null || response.getAccessToken() == null) {
                logger.error("Failed to generate webhook: response or fields are null");
                return;
            }

            String webhookUrl = response.getWebhook();
            String accessToken = response.getAccessToken();

            String finalQuery = "SELECT p.AMOUNT AS SALARY, " +
                               "CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME, " +
                               "TIMESTAMPDIFF(YEAR, e.DOB, '2025-05-11') AS AGE, " +
                               "d.DEPARTMENT_NAME " +
                               "FROM PAYMENTS p " +
                               "JOIN EMPLOYEE e ON p.EMP_ID = e.EMP_ID " +
                               "JOIN DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID " +
                               "WHERE p.AMOUNT = (SELECT MAX(AMOUNT) FROM PAYMENTS WHERE DAY(PAYMENT_TIME) != 1) " +
                               "AND DAY(p.PAYMENT_TIME) != 1";

            SQLQueryreq submitRequest = new SQLQueryreq(finalQuery);
            String submitResponse = services.FQuery(webhookUrl, accessToken, submitRequest);

            if (submitResponse != null) {
                System.out.println("Submission Response: " + submitResponse);
            } else {
                logger.error("Failed to submit query: response is null");
            }
        } catch (Exception e) {
            logger.error("Error executing application: {}", e.getMessage(), e);
            throw e;
        }
    }
}