package com.library.service;
import com.library.model.Book;
import com.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Long id, Book book) {
        bookRepository.update(id, book);
    }

    public void deleteBook(Long id) {
        bookRepository.delete(id);
    }

    public List<Book> searchBooks(String title, String author, String isbn) {
        return bookRepository.search(title, author, isbn);
    }
}