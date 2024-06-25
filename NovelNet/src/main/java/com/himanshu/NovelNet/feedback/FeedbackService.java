package com.himanshu.NovelNet.feedback;

import com.himanshu.NovelNet.book.Book;
import com.himanshu.NovelNet.book.BookRepository;
import com.himanshu.NovelNet.commom.PageResponse;
import com.himanshu.NovelNet.exception.OperationNotAllowedException;
import com.himanshu.NovelNet.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Slf4j
public class FeedbackService {
    private final BookRepository bookRepository;
    private final FeedbackMapper feedbackMapper;
    private final FeedbackRepository feedbackRepository;

    public Long saveFeedback(FeedbackRequest feedbackRequest, Authentication connectedUser) {
        Book book = bookRepository.findById(feedbackRequest.bookId())
                .orElseThrow(() -> new EntityNotFoundException("No book found with given Id"));

        if (book.isArchived() || !book.isShareable()) {
            throw new OperationNotAllowedException("Archived or non shared books all prorated for the service!");
        }
        User user = (User) connectedUser.getPrincipal();

        if (Objects.equals(user.getId(), book.getOwner().getId())) {
            throw new OperationNotAllowedException("You can not give feedback to your own book!");
        }

        Feedback feedback = feedbackMapper.toFeedback(feedbackRequest);

        Feedback save = feedbackRepository.save(feedback);
        return save.getId();
    }

    public PageResponse<FeedbackResponse> findAllFeedbacksByBook(Long bookId, int page, int size, Authentication connectedUser) {
        Pageable pageable = PageRequest.of(page, size);
        User user = (User) connectedUser.getPrincipal();

        Page<Feedback> feedbackPage = feedbackRepository.findAllFeedbacksByBookId(pageable, bookId);

        List<FeedbackResponse> responseList = feedbackPage.stream()
                .map(feedback -> feedbackMapper.toFeedbackResponse(feedback, user.getId()))
                .toList();
        return new PageResponse<>(
                responseList,
                feedbackPage.getNumber(),
                feedbackPage.getSize(),
                feedbackPage.getTotalElements(),
                feedbackPage.getTotalPages(),
                feedbackPage.isFirst(),
                feedbackPage.isLast());
    }
}
