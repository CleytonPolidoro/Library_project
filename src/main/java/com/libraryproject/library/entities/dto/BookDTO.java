package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookDTO {

    private Long id;
    private String title;
    private String author;
    private Integer pages;
    private Long isbn;
    private Double price;
    private String imgUrl;
    private boolean available;


    public BookDTO(Long id, String title, String author, Integer pages, Long isbn, Double price, String imgUrl, boolean available) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
        this.price = price;
        this.imgUrl = imgUrl;
        this.available = available;
    }

    public BookDTO(Book entity){
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        pages = entity.getPages();
        isbn = entity.getIsbn();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        available = entity.isAvailable();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public Integer getPages() {
        return pages;
    }

    public Long getIsbn() {
        return isbn;
    }

    public Double getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }


    public boolean isAvailable() {
        return available;
    }

}
