package com.himanshu.NovelNet.book;

import com.himanshu.NovelNet.commom.BaseEntity;
import com.himanshu.NovelNet.feedback.Feedback;
import com.himanshu.NovelNet.history.BookTransactionHistory;
import com.himanshu.NovelNet.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Book extends BaseEntity {

    private String title;

    private String authorName;

    private String isbn; //identifier;

    private String synopsis;

    private String bookCover;

    private boolean archivied;

    private boolean shareable;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "book")
    private List<Feedback> feedbacks;

    @OneToMany(mappedBy = "book")
    private List<BookTransactionHistory> history;

    @Transient
    public double getRatings() {
        if (feedbacks == null || feedbacks.isEmpty()) {
            return 0D;
        }
        double rating = feedbacks.stream().mapToDouble(Feedback::getNote).average().orElse(0D);
        return Math.round(rating);
    }

}
