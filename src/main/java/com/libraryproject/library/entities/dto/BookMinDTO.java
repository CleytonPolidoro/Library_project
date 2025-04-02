package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Book;

public class BookMinDTO {

    private Long id;
    private String title;
    private String author;
    private Double price;

    public BookMinDTO(Long id, String author, String title, Double price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
    }

    public BookMinDTO(Book entity){
        id = entity.getId();
        author = entity.getAuthor();
        title = entity.getTitle();
        price = entity.getPrice();
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }
}
