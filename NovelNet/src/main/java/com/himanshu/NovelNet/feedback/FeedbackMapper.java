package com.himanshu.NovelNet.feedback;

import com.himanshu.NovelNet.book.Book;
import org.springframework.stereotype.Service;

@Service
public class FeedbackMapper {
    public Feedback toFeedback(FeedbackRequest feedbackRequest) {
        return Feedback.builder()
                .note(feedbackRequest.note())
                .comment(feedbackRequest.comment())
                .book(Book.builder()
                        .id(feedbackRequest.bookId())
                        .build())
                .build();
    }

    public FeedbackResponse toFeedbackResponse(Feedback feedback, Long id) {
        return FeedbackResponse.builder()
                .ownFeedback(feedback.getCreatedBy().equals(id))
                .comment(feedback.getComment())
                .note(feedback.getNote())
                .build();
    }
}
