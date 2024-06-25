package com.himanshu.NovelNet.book;

import com.himanshu.NovelNet.history.BookTransactionHistory;
import org.springframework.data.jpa.domain.Specification;

public class BookSpecification {
    public static Specification<Book> withOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("owner").get("id"), ownerId);
    }

    public static Specification<BookTransactionHistory> returnedByOwner(Long ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("book").get("owner").get("id"), ownerId);
    }
}
