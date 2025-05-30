package com.library.repository;

import com.library.model.Borrow;
import com.library.model.Book;
import com.library.model.Member;
import com.library.exception.BookUnavailableException;
import com.library.exception.BorrowNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public class BorrowRepository {

    private final JdbcTemplate jdbcTemplate;
    private final BookRepository bookRepository; // Inject BookRepository
    private final MemberRepository memberRepository; // Inject MemberRepository

    public BorrowRepository(JdbcTemplate jdbcTemplate, BookRepository bookRepository, MemberRepository memberRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.bookRepository = bookRepository;
        this.memberRepository = memberRepository;
    }

    public class BookUnavailableException extends RuntimeException { // Or Exception if you want checked exception

        public BookUnavailableException(String message) {
            super(message);
        }
    }

    public Borrow findById(Long id) {
        String sql = "SELECT id, member_id, book_id, borrowed_date, due_date FROM borrow WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                    new Borrow(
                            rs.getLong("id"),
                            rs.getLong("member_id"),
                            rs.getLong("book_id"),
                            rs.getDate("borrowed_date").toLocalDate(),
                            rs.getDate("due_date").toLocalDate()
                    ));
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + id);
        }
    }

    public List<Borrow> findAll() {
        String sql = "SELECT id, member_id, book_id, borrowed_date, due_date FROM borrow";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Borrow(
                        rs.getLong("id"),
                        rs.getLong("member_id"),
                        rs.getLong("book_id"),
                        rs.getDate("borrowed_date").toLocalDate(),
                        rs.getDate("due_date").toLocalDate()
                ));
    }

    @Transactional // Ensure atomicity
    public void save(Borrow borrow) {
        // Check if the book is available
        Book book = bookRepository.findById(borrow.getBookId());
        if (book.getAvailableCopies() <= 0) {
            throw new BookUnavailableException("Book with id " + borrow.getBookId() + " is not available for borrowing.");
        }

        // Update available copies
        String updateBookSql = "UPDATE book SET available_copies = available_copies - 1 WHERE id = ?";
        jdbcTemplate.update(updateBookSql, borrow.getBookId());

        // Create a new Borrow record
        String insertBorrowSql = "INSERT INTO borrow (member_id, book_id, borrowed_date, due_date) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(insertBorrowSql, borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowedDate(), borrow.getDueDate());
    }

    @Transactional
    public void update(Long id, Borrow borrow) {
        String sql = "UPDATE borrow SET member_id = ?, book_id = ?, borrowed_date = ?, due_date = ? WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, borrow.getMemberId(), borrow.getBookId(), borrow.getBorrowedDate(), borrow.getDueDate(), id);
        if (rowsAffected == 0) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + id);
        }
    }

    public void delete(Long id) {
        String sql = "DELETE FROM borrow WHERE id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id);
        if (rowsAffected == 0) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + id);
        }
    }

    @Transactional
    public void returnBook(Long borrowId) {
        Borrow borrow = findById(borrowId);
        if (borrow == null) {
            throw new BorrowNotFoundException("Borrow record not found with id: " + borrowId);
        }

        // Increment available copies
        String updateBookSql = "UPDATE book SET available_copies = available_copies + 1 WHERE id = ?";
        jdbcTemplate.update(updateBookSql, borrow.getBookId());

        // Delete the borrow record (or update a returned_date column if you want to keep history)
        String deleteBorrowSql = "DELETE FROM borrow WHERE id = ?";
        jdbcTemplate.update(deleteBorrowSql, borrowId);
    }

    public List<Borrow> findAllWithBookAndMemberDetails() {
        String sql = "SELECT " +
                "b.id AS borrow_id, " +
                "b.member_id, " +
                "b.book_id, " +
                "b.borrowed_date, " +
                "b.due_date, " +
                "book.title AS book_title, " +
                "book.author AS book_author, " +
                "member.name AS member_name, " +
                "member.phone AS member_phone " +
                "FROM borrow b " +
                "JOIN book ON b.book_id = book.id " +
                "JOIN member ON b.member_id = member.id";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Borrow borrow = new Borrow();
            borrow.setId(rs.getLong("borrow_id"));
            borrow.setMemberId(rs.getLong("member_id"));
            borrow.setBookId(rs.getLong("book_id"));
            borrow.setBorrowedDate(rs.getDate("borrowed_date").toLocalDate());
            borrow.setDueDate(rs.getDate("due_date").toLocalDate());

            // Add Book details to the Borrow object (you might want to create a DTO for this)
            Book book = new Book();
            book.setTitle(rs.getString("book_title"));
            book.setAuthor(rs.getString("book_author"));
            // Set other book details if needed
            //borrow.setBook(book); // Assuming you have a setBook method

            // Add Member details to the Borrow object (again, consider a DTO)
            Member member = new Member();
            member.setName(rs.getString("member_name"));
            member.setPhone(rs.getString("member_phone"));
            // Set other member details if needed
            //borrow.setMember(member); // Assuming you have a setMember method

            return borrow;
        });
    }
}