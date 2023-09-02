package com.polarbookshop.catalogservice.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

@JsonTest
class BookJsonTests {

    @Autowired
    JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        var expected = new Book("0123456789", "Title", "Author", 9.99);

        var actual = json.write(expected);

        Assertions.assertThat(actual).extractingJsonPathStringValue("@.isbn").isEqualTo(expected.isbn());
        Assertions.assertThat(actual).extractingJsonPathStringValue("@.title").isEqualTo(expected.title());
        Assertions.assertThat(actual).extractingJsonPathStringValue("@.author").isEqualTo(expected.author());
        Assertions.assertThat(actual).extractingJsonPathNumberValue("@.price").isEqualTo(expected.price());
    }

    @Test
    void testDeserialize() throws Exception {
        var expected = new Book("0123456789", "Title", "Author", 9.99);

        var content = """ 
                {
                    "isbn": "0123456789",
                    "title": "Title",
                    "author": "Author",
                    "price": 9.99
                }
                """;

        Assertions.assertThat(json.parse(content)).usingRecursiveComparison().isEqualTo(expected);
    }

}