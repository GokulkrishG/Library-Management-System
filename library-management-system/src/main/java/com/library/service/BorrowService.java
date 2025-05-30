package com.library.service;

import com.library.model.Borrow;
import com.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {

    private final BorrowRepository borrowRepository;

    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    public Borrow getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    public List<Borrow> getAllBorrows() {
        return borrowRepository.findAll();
    }

    public void createBorrow(Borrow borrow) {
        borrowRepository.save(borrow);
    }

    public void updateBorrow(Long id, Borrow borrow) {
        borrowRepository.update(id, borrow);
    }

    public void deleteBorrow(Long id) {
        borrowRepository.delete(id);
    }

    public void returnBook(Long borrowId) {
        borrowRepository.returnBook(borrowId);
    }

    public List<Borrow> getAllBorrowsWithBookAndMemberDetails() {
        return borrowRepository.findAllWithBookAndMemberDetails();
    }
}