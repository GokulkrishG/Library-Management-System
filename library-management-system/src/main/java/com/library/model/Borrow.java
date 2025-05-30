package com.library.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {
    private Long id;
    private Long memberId;
    private Long bookId;
    private LocalDate borrowedDate;
    private LocalDate dueDate;
}