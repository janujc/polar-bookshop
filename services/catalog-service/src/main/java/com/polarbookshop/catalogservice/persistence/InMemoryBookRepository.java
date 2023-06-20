package com.polarbookshop.catalogservice.persistence;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.polarbookshop.catalogservice.domain.Book;
import com.polarbookshop.catalogservice.domain.BookRepository;

@Repository
public class InMemoryBookRepository implements BookRepository {

    private static final Map<String, Book> books = new ConcurrentHashMap<>();

    @Override
    public void deleteByIsbn(String isbn) {
        books.remove(isbn);
    }

    @Override
    public boolean existsByIsbn(String isbn) {
        return books.containsKey(isbn);
    }

    @Override
    public Iterable<Book> findAll() {
        return books.values();
    }

    @Override
    public Optional<Book> findByIsbn(String isbn) {
        return Optional.ofNullable(books.get(isbn));
    }

    @Override
    public Book save(Book book) {
        return books.put(book.getIsbn(), book);
    }
}
