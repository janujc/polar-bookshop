package com.polarbookshop.catalogservice.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class BookValidationTests {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSucceeds() {
        var book = new Book("1234567890", "Title", "Author", 9.99);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).isEmpty();
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFails() {
        String expected = "The ISBN format must be valid.";

        var book = new Book("a234567890", "Title", "Author", 9.99);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo(expected);
    }

    @Test
    void whenIsbnUndefinedThenValidationFails() {
        Set<String> expected = Set.of("The book ISBN must be defined.", "The ISBN format must be valid.");

        var book = new Book("", "Title", "Author", 9.99);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(2);
        Assertions.assertThat(violations)
                  .extracting(ConstraintViolation::getMessage)
                  .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    void whenTitleUndefinedThenValidationFails() {
        String expected = "The book title must be defined.";

        var book = new Book("0123456789", "", "Author", 9.99);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo(expected);
    }

    @Test
    void whenAuthorUndefinedThenValidationFails() {
        String expected = "The book author must be defined.";

        var book = new Book("0123456789", "Title", "", 9.99);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo(expected);
    }

    @Test
    void whenPriceUndefinedThenValidationFails() {
        String expected = "The book price must be defined.";

        var book = new Book("0123456789", "Title", "Author", null);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo(expected);
    }

    @Test
    void whenPriceLessThanOrEqualToZeroThenValidationFails() {
        String expected = "The book price must be greater than zero.";

        var book = new Book("0123456789", "Title", "Author", 0.00);

        Set<ConstraintViolation<Book>> violations = validator.validate(book);

        Assertions.assertThat(violations).hasSize(1);
        Assertions.assertThat(violations.iterator().next().getMessage()).isEqualTo(expected);
    }

}
