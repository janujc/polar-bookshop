package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.domain.Book;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CatalogServiceApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void whenPostRequestThenBookCreated() {
        var expectedBook = new Book("0123456789", "Title", "Author", 9.90);

        webTestClient.post()
                     .uri("/books")
                     .bodyValue(expectedBook)
                     .exchange()
                     .expectStatus()
                     .isCreated()
                     .expectBody(Book.class)
                     .value(actualBook -> Assertions.assertThat(actualBook).isEqualTo(expectedBook));
    }

}
