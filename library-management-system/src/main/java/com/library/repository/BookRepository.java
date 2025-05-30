package com.library.repository;

import com.library.model.Book;
import com.library.exception.BookNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Book findById(Long id) {
        String sql = "SELECT id, title, author, isbn, published_date, available_copies FROM book WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Book(
                            rs.getLong("id"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("isbn"),
                            rs.getDate("published_date").toLocalDate(),
                            rs.getInt("available_copies")
                    ));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    public List<Book> findAll() {
        String sql = "SELECT id, title, author, isbn, published_date, available_copies FROM book";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDate("published_date").toLocalDate(),
                        rs.getInt("available_copies")
                ));
    }

    public void save(Book book) {
        String sql = "INSERT INTO book (title, author, isbn, published_date, available_copies) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies());
    }

    public void update(Long id, Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, isbn = ?, published_date = ?, available_copies = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, book.getTitle(), book.getAuthor(), book.getIsbn(), book.getPublishedDate(), book.getAvailableCopies(), id);
        if (rowsAffected == 0) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM book WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new BookNotFoundException("Book not found with id: " + id);
        }
    }

    public List<Book> search(String title, String author, String isbn) {
        String sql = "SELECT id, title, author, isbn, published_date, available_copies FROM book WHERE 1=1";
        if (title != null && !title.isEmpty()) {
            sql += " AND title LIKE '%" + title + "%'";
        }
        if (author != null && !author.isEmpty()) {
            sql += " AND author LIKE '%" + author + "%'";
        }
        if (isbn != null && !isbn.isEmpty()) {
            sql += " AND isbn = '" + isbn + "'";
        }

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Book(
                        rs.getLong("id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getDate("published_date").toLocalDate(),
                        rs.getInt("available_copies")
                ));
    }
}