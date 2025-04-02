package com.libraryproject.library.entities.dto;

import com.libraryproject.library.entities.Book;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;


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

    @NotEmpty(message = "Deve ter no mínimo um gênero")
    private List<GenderDTO> genders = new ArrayList<>();


    public BookDTO(Long id, String title, String author, Integer pages, Long isbn, Double price, String imgUrl, List<GenderDTO> genders) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
        this.price = price;
        this.imgUrl = imgUrl;
        this.genders.addAll(genders);
    }

    public  BookDTO(Book entity){
        id = entity.getId();
        title = entity.getTitle();
        author = entity.getAuthor();
        pages = entity.getPages();
        isbn = entity.getIsbn();
        price = entity.getPrice();
        imgUrl = entity.getImgUrl();
        genders.addAll(entity.getGenders().stream().map(x -> new GenderDTO(x)).toList());
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

    public List<GenderDTO> getGenders() {
        return genders;
    }

}
