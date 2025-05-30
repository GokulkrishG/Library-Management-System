package com.library.controller;

import com.library.model.Borrow;
import com.library.service.BorrowService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/borrows")
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Borrow> getBorrowById(@PathVariable Long id) {
        Borrow borrow = borrowService.getBorrowById(id);
        return new ResponseEntity<>(borrow, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Borrow>> getAllBorrows() {
        List<Borrow> borrows = borrowService.getAllBorrows();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> createBorrow(@RequestBody Borrow borrow) {
        borrowService.createBorrow(borrow);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateBorrow(@PathVariable Long id, @RequestBody Borrow borrow) {
        borrowService.updateBorrow(id, borrow);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBorrow(@PathVariable Long id) {
        borrowService.deleteBorrow(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnBook(@PathVariable Long id) {
        borrowService.returnBook(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/details")
    public ResponseEntity<List<Borrow>> getAllBorrowsWithBookAndMemberDetails() {
        List<Borrow> borrows = borrowService.getAllBorrowsWithBookAndMemberDetails();
        return new ResponseEntity<>(borrows, HttpStatus.OK);
    }
}