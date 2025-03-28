package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class BookDTO {

    private Long id;
    @Size(min = 3, message = "O valor tem que no mínimo 3 caracteres.")
    @NotBlank(message = "O valor não pode ser vazio")
    private String title;

    @Size(min = 3, message = "O valor tem que no mínimo 3 caracteres.")
    @NotBlank(message = "O valor não pode ser vazio")
    private String author;
    @NotNull
    private Integer pages;
    private Long isbn;
    @NotNull(message = "Campo requerido")
    @Positive(message = "O valor tem que ser positivo")
    private Double price;
    private String imgUrl;


    public BookDTO(Long id, String title, String author, Integer pages, Long isbn, Double price, String imgUrl) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public BookDTO(Book entity){
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        pages = entity.getPages();
        isbn = entity.getIsbn();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
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



}
