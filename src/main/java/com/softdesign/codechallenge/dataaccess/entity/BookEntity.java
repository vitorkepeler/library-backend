package com.softdesign.codechallenge.dataaccess.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "book")
@Getter
@Setter
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Lob
    private String synopsis;
    private String author;
    private String category;
    private String publicationYear;
    private boolean available;
}
