package com.himanshu.NovelNet.book;

import com.himanshu.NovelNet.commom.PageResponse;
import com.himanshu.NovelNet.exception.OperationNotAllowedException;
import com.himanshu.NovelNet.history.BookTransactionHistory;
import com.himanshu.NovelNet.history.BookTransactionHistoryRepository;
import com.himanshu.NovelNet.storage.FileStorageService;
import com.himanshu.NovelNet.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookMapper bookMapper;
    private final BookRepository bookRepository;
    private final BookTransactionHistoryRepository historyRepository;
    private final FileStorageService fileStorageService;

    public Long save(BookRequest request, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Book book = bookMapper.toBook(request);
        book.setOwner(user);

        Book save = bookRepository.save(book);

        return save.getId();
    }

    public MyBookResponse getByID(Long bookId) {
        return bookRepository.findById(bookId)
                .map(bookMapper::toBookResponse)
                .orElseThrow(() -> new EntityNotFoundException("No book found with id :" + bookId));
    }

    public PageResponse<MyBookResponse> getBooksByPage(int page, int size, Authentication connectedUser) {

        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAllDisplayableBooks(pageable, user.getId());
        List<MyBookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast()
        );
    }

    public PageResponse<MyBookResponse> findAllBooksByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Book> books = bookRepository.findAll(BookSpecification.withOwnerId(user.getId()), pageable);
        List<MyBookResponse> booksResponse = books.stream()
                .map(bookMapper::toBookResponse)
                .toList();
        return new PageResponse<>(
                booksResponse,
                books.getNumber(),
                books.getSize(),
                books.getTotalElements(),
                books.getTotalPages(),
                books.isFirst(),
                books.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllBorrowedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> transactionHistories = historyRepository.findAllBorrowedBookHistory(pageable, user.getId());
        List<BorrowedBookResponse> borrowedBookResponseList = transactionHistories.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                borrowedBookResponseList,
                transactionHistories.getNumber(),
                transactionHistories.getSize(),
                transactionHistories.getTotalElements(),
                transactionHistories.getTotalPages(),
                transactionHistories.isFirst(),
                transactionHistories.isLast());
    }

    public PageResponse<BorrowedBookResponse> findAllReturnedBooks(int page, int size, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<BookTransactionHistory> transactionHistories = historyRepository.findAll(BookSpecification.returnedByOwner(user.getId()), pageable);
        List<BorrowedBookResponse> borrowedBookResponseList = transactionHistories.stream()
                .map(bookMapper::toBorrowedBookResponse)
                .toList();

        return new PageResponse<>(
                borrowedBookResponseList,
                transactionHistories.getNumber(),
                transactionHistories.getSize(),
                transactionHistories.getTotalElements(),
                transactionHistories.getTotalPages(),
                transactionHistories.isFirst(),
                transactionHistories.isLast());
    }

    public Long updateShareableStatus(Long bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not update books, you don't own");
        }

        book.setShareable(!book.isShareable());
        bookRepository.save(book);
        return bookId;
    }

    public Long updateArchiveStatus(Long bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));
        User user = (User) connectedUser.getPrincipal();
        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not update books, you don't own");
        }

        book.setArchivied(!book.isArchivied());
        bookRepository.save(book);
        return bookId;
    }

    public Long borrowBook(Long bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));

        if (book.isArchivied() || !book.isShareable()) {
            throw new OperationNotAllowedException("Requested book can not be shared!!");
        }
        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not borrow/return your own books!");
        }

        final boolean isAlreadyBorrowed = historyRepository.isAlreadyBorrowed(bookId);
        if (isAlreadyBorrowed) {
            throw new OperationNotAllowedException("Requested book is already Borrowed!");
        }

        BookTransactionHistory transactionHistory = BookTransactionHistory.builder()
                .user(user)
                .book(book)
                .returnApproved(false)
                .isReturned(false)
                .build();

        return historyRepository.save(transactionHistory).getId();

    }

    public Long returnBorrowedBook(Long bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));

        if (book.isArchivied() || !book.isShareable()) {
            throw new OperationNotAllowedException("Requested book can not be shared!!");
        }
        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not borrow/return your own books!");
        }


        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndUserId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotAllowedException("You can not return an book, that is not borrowed by you!"));

        bookTransactionHistory.setReturned(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    public Long approveReturnBorrowedBook(Long bookId, Authentication connectedUser) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));

        if (book.isArchivied() || !book.isShareable()) {
            throw new OperationNotAllowedException("Requested book can not be shared!!");
        }
        User user = (User) connectedUser.getPrincipal();

        if (!Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can only approve  your own books!");
        }

        BookTransactionHistory bookTransactionHistory = historyRepository.findByBookIdAndOwnerrId(bookId, user.getId())
                .orElseThrow(() -> new OperationNotAllowedException("Book is not returned yet!"));

        bookTransactionHistory.setReturnApproved(true);
        return historyRepository.save(bookTransactionHistory).getId();
    }

    public void uploadBookCover(MultipartFile file, Authentication connectedUser, Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));

        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not borrow/return your own books!");
        }

        String coverPath = fileStorageService.saveFile(file, book, user.getId());
        book.setBookCover(coverPath);
        bookRepository.save(book);
    }
}
