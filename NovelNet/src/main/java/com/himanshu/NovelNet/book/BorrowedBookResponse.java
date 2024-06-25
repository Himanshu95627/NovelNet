package com.himanshu.NovelNet.book;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BorrowedBookResponse {
    private Long id;
    private String title;

    private String authorName;

    private String isbn; //identifier;
    private boolean isReturned;

    private boolean returnApproved;

    private double ratings;
}
