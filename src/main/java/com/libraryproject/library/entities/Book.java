package com.libraryproject.library.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.libraryproject.library.entities.dto.BookDTO;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "books")
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private Integer pages;
    private Long isbn;
    private Double price;
    private String imgUrl;


    @ManyToMany
    @JsonIgnoreProperties("books")
    @JoinTable(name = "book_gender",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "gender_id")
    )
    private Set<Gender> genders = new HashSet<>();

    @OneToMany(mappedBy = "id.book")
    private Set<OrderItem> items = new HashSet<>();

    public Book() {}

    public Book(Long id, String title, String author, Integer pages,
                Long isbn, Double price, String imgUrl, Gender gender) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.isbn = isbn;
        this.price = price;
        this.imgUrl = imgUrl;
        this.genders.add(gender);
    }

    public Book(BookDTO entity){
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

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Set<Gender> getGenders() {
        return genders;
    }

    public void addGender(Gender gender){
        genders.add(gender);
    }

    public Set<OrderItem> getItems(){
        return items;
    }

    @JsonIgnore
    public List<Order> getOrders() {

        return items.stream().map(x -> x.getOrder()).toList();

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(id, book.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
