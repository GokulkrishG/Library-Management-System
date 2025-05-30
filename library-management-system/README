**Task: Develop a Library Management System for Librarians**

**Objective:** The goal is to design and implement a library management system using Spring Boot, Spring MVC, and PostgreSQL. This system will be used exclusively by librarians, and there's no need for authentication as members will not use this tool.

**Git-Flow:** You are tasked to implement this project in a git-flow. To achieve this, first, create a 'develop' branch from your main branch. Then, create a 'feature' branch based on this 'develop' branch and proceed with the task activities. After finishing the task, create a Merge Request (MR) to merge the changes from your 'feature' branch into the 'develop' branch. The reviewer will review, approve, and merge the MR.

**Requirements:**

1. **Book Management:**
    - Create a `Book` entity with attributes: `id`, `title`, `author`, `isbn`, `publishedDate`, and `availableCopies`.
    - Build REST API endpoints to cover CRUD operations for the `Book` entity using `JdbcTemplate` with raw SQL queries.
    - Implement a search functionality to find books by `title`, `author`, or `isbn` using `JdbcTemplate` with raw SQL queries.

2. **Member Management:**
    - Create a `Member` entity with attributes: `id`, `name`, `phone`, `registeredDate`.
    - Build REST API endpoints to cover CRUD operations for the `Member` entity using `JdbcTemplate` with raw SQL queries.

3. **Borrowing Management:**
    - Create a `Borrow` entity with attributes: `id`, `memberId`, `bookId`, `borrowedDate`, `dueDate`.
    - Build REST API endpoints to cover CRUD operations for the `Borrow` entity using `JdbcTemplate` with raw SQL queries.
    - A librarian can lend a book to a member if it's available (i.e., `availableCopies` > 0). Update `availableCopies` by decrementing it by 1 and add a new `Borrow` record using `JdbcTemplate` with raw SQL queries.
    - A librarian can accept a returned book. Update `availableCopies` by incrementing it by 1 and update the `Borrow` record using `JdbcTemplate` with raw SQL queries.
    - For the API endpoints of Borrowing Management, join the `Book` and `Member` details with the `Borrow` entity. The response should include `book` and `member` attributes along with other attributes of the `Borrow` entity.

4. **Exception Handling:**
    - Handle exceptions properly, for example, when a book is not available for borrowing or a member tries to borrow more than a certain limit.

5. **Database:**
    - Use PostgreSQL for the database.
    - Use `JdbcTemplate` for interacting with the database using raw SQL queries.
    - Add database migration scripts so that when you run the Spring Boot application, the database tables are automatically created.

6. **Testing:**
    - Write integration tests for the controllers using `mockMvc`. The tests should cover all possible scenarios and edge cases to achieve full coverage. Assertions should be made for all attributes in the response to ensure the correctness of the data.
    - Wherever possible, use `ParameterizedTest` and `Nested` tests to reuse test code, better organize the tests, and ensure the right order of test execution.

**Deliverables:**

- Source code in this private repository.