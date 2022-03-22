package com.gui.estore.productservice.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsAccceptanceTest {

    @Value("${local.server.port}")
    private int randomPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createProduct() throws URISyntaxException {
        // given
//        URI createProductURI = new URI("http://localhost:" + randomPort + "/products");

        ResponseEntity<String> response = restTemplate.postForEntity("/products", null, String.class);

//        assertThat(response.getHeaders().getLocation()).isEqualTo(createProductURI);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isEqualTo("lalala POST");
    }
}