package com.himanshu.NovelNet.book;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyBookResponse {
    private Long id;

    private String title;

    private String authorName;

    private String isbn; //identifier;

    private String synopsis;

    private String owner;

    private byte[] bookCover;

    private double ratings;

    private boolean archivied;

    private boolean shareable;

}
