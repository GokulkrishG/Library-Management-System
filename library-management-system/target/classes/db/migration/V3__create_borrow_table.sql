CREATE TABLE borrow (
    id BIGSERIAL PRIMARY KEY,
    member_id BIGINT REFERENCES member(id),
    book_id BIGINT REFERENCES book(id),
    borrowed_date DATE,
    due_date DATE
);