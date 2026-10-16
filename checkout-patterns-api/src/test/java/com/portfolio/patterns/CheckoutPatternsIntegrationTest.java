package com.portfolio.patterns;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CheckoutPatternsIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createsAndRetrievesOrderUsingPixAndStandardShipping() throws Exception {
        String request = """
                {
                  "customerName": "Ana Silva",
                  "paymentMethod": "PIX",
                  "shippingMethod": "STANDARD",
                  "items": [
                    {"productCode": "JAVA-CLEAN-CODE", "quantity": 2},
                    {"productCode": "GOF-PATTERNS", "quantity": 1}
                  ]
                }
                """;

        ResponseEntity<String> response = postOrder(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        JsonNode body = objectMapper.readTree(response.getBody());
        assertThat(body.get("customerName").asText()).isEqualTo("Ana Silva");
        assertThat(body.get("subtotal").decimalValue()).isEqualByComparingTo("169.70");
        assertThat(body.get("shippingCost").decimalValue()).isEqualByComparingTo("6.90");
        assertThat(body.get("total").decimalValue()).isEqualByComparingTo("176.60");
        assertThat(body.get("paymentReference").asText()).startsWith("SIMULATED-PIX-");
        assertThat(body.get("items").size()).isEqualTo(2);

        long id = body.get("id").asLong();
        ResponseEntity<String> retrieved = restTemplate.getForEntity("/api/orders/" + id, String.class);
        assertThat(retrieved.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(objectMapper.readTree(retrieved.getBody()).get("id").asLong()).isEqualTo(id);
    }

    @Test
    void selectsCardAndExpressStrategies() throws Exception {
        String request = """
                {
                  "customerName": "Rui Costa",
                  "paymentMethod": "CARD",
                  "shippingMethod": "EXPRESS",
                  "items": [{"productCode": "SPRING-REST", "quantity": 1}]
                }
                """;
        ResponseEntity<String> response = postOrder(request);
        JsonNode body = objectMapper.readTree(response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(body.get("shippingCost").decimalValue()).isEqualByComparingTo("16.90");
        assertThat(body.get("paymentReference").asText()).startsWith("SIMULATED-CARD-");
    }

    @Test
    void rejectsUnknownProductBeforeCreatingOrder() {
        String request = """
                {
                  "customerName": "Ana Silva",
                  "paymentMethod": "PIX",
                  "shippingMethod": "STANDARD",
                  "items": [{"productCode": "NOT-A-BOOK", "quantity": 1}]
                }
                """;
        ResponseEntity<String> response = postOrder(request);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).contains("Produto não encontrado");
    }

    @Test
    void returnsNotFoundForMissingOrder() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/orders/999999", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> postOrder(String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return restTemplate.exchange("/api/orders", HttpMethod.POST,
                new HttpEntity<>(json, headers), String.class);
    }
}
