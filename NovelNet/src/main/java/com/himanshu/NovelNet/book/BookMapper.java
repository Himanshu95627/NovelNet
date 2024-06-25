package com.himanshu.NovelNet.book;

import com.himanshu.NovelNet.history.BookTransactionHistory;
import com.himanshu.NovelNet.storage.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookMapper {

    public Book toBook(BookRequest request) {
        return Book.builder()
                .id(request.id())
                .title(request.title())
                .isbn(request.isbn())
                .authorName(request.authorName())
                .archivied(false)
                .shareable(request.shareable())
                .build();
    }

    public MyBookResponse toBookResponse(Book book) {
        return MyBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .ratings(book.getRatings())
                .archivied(book.isArchivied())
                .shareable(book.isShareable())
                .owner(book.getOwner().getFullName())
                .bookCover(FileUtil.readFileFromLocation(book.getBookCover()))
                .build();
    }

    public BorrowedBookResponse toBorrowedBookResponse(BookTransactionHistory bookTransactionHistory) {
        Book book = bookTransactionHistory.getBook();
        return BorrowedBookResponse.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .ratings(book.getRatings())
                .returnApproved(bookTransactionHistory.isReturnApproved())
                .isReturned(bookTransactionHistory.isReturned())
                .build();

    }
}
