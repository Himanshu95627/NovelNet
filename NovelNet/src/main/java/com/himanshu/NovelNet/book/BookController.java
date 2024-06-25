package com.himanshu.NovelNet.book;

import com.himanshu.NovelNet.commom.PageResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("books")
@RequiredArgsConstructor
@Tag(name = "Book")
public class BookController {

    private final BookService bookService;

    @PostMapping("/save")
    public ResponseEntity<Long> saveBook(@RequestBody @Valid BookRequest request, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.save(request, connectedUser));
    }

    @GetMapping("{book-id}")
    public ResponseEntity<MyBookResponse> getBookByID(@PathVariable(name = "book-id") Long bookId) {
        return ResponseEntity.ok(bookService.getByID(bookId));
    }

    @GetMapping
    public ResponseEntity<PageResponse<MyBookResponse>> findAllBooks(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                     @RequestParam(name = "size", defaultValue = "0", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.getBooksByPage(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<MyBookResponse>> findAllBooksBelongToOwner(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                  @RequestParam(name = "size", defaultValue = "0", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllBooksByOwner(page, size, connectedUser));
    }

    @GetMapping("/borrowed")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllBorrowedBooksByUser(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                         @RequestParam(name = "size", defaultValue = "0", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllBorrowedBooks(page, size, connectedUser));
    }

    @GetMapping("/returned")
    public ResponseEntity<PageResponse<BorrowedBookResponse>> findAllReturnedBooksByUser(@RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                                         @RequestParam(name = "size", defaultValue = "0", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.findAllReturnedBooks(page, size, connectedUser));
    }

    @PatchMapping("/shareable/{book-id}")
    public ResponseEntity<Long> updateShareableStatus(@PathVariable("book-id") Long bookId,
                                                      Authentication connectedUser) {
        return ResponseEntity.ok(bookService.updateShareableStatus(bookId, connectedUser));
    }

    @PatchMapping("/archive/{book-id}")
    public ResponseEntity<Long> updateArchiveStatus(@PathVariable("book-id") Long bookId,
                                                    Authentication connectedUser) {
        return ResponseEntity.ok(bookService.updateArchiveStatus(bookId, connectedUser));
    }

    @PostMapping("/borrow/{book-id}")
    public ResponseEntity<Long> borrowBook(@PathVariable("book-id") Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.borrowBook(bookId, connectedUser));
    }

    @PatchMapping("/return/{book-id}")
    public ResponseEntity<Long> returnBorrowedBook(@PathVariable("book-id") Long bookId,
                                                   Authentication connectedUser) {
        return ResponseEntity.ok(bookService.returnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping("/borrow/return/approve/{book-id}")
    public ResponseEntity<Long> arrpoveReturnBorrowedBook(@PathVariable("book-id") Long bookId, Authentication connectedUser) {
        return ResponseEntity.ok(bookService.approveReturnBorrowedBook(bookId, connectedUser));
    }

    @PostMapping(value = "/cover/{book-id}", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadBookCover(@PathVariable("book-id") Long bookId, @Parameter()
    @RequestPart("file") MultipartFile file, Authentication connectedUser) {
        bookService.uploadBookCover(file, connectedUser, bookId);
        return ResponseEntity.accepted().build();
    }
}
