package com.himanshu.NovelNet.feedback;

import com.himanshu.NovelNet.commom.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("feedback")
@RequiredArgsConstructor
@Tag(name = "feedback")
public class FeedbackController {
    private final FeedbackService feedbackService;

    @PostMapping
    public ResponseEntity<Long> saveFeedback(@RequestBody @Valid FeedbackRequest feedbackRequest, Authentication connectedUser) {
        return ResponseEntity.ok(feedbackService.saveFeedback(feedbackRequest, connectedUser));
    }

    @GetMapping("/book/{book-id}")
    public ResponseEntity<PageResponse<FeedbackResponse>> allFeedbacksByBook(@PathVariable("book-id") Long bookId,
                                                                             @RequestParam(name = "page", defaultValue = "0", required = false) int page,
                                                                             @RequestParam(name = "size", defaultValue = "0", required = false) int size, Authentication connectedUser) {
        return ResponseEntity.ok(feedbackService.findAllFeedbacksByBook(bookId, page, size, connectedUser));
    }
}
