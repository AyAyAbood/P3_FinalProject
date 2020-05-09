/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.sql.Date;

/**
 *
 * @author AboOod_shbika99
 */
public class book {
    private int id;
    private String title;
    private String author;
    private Double price;
    private String publisher;
    private String genre;
    private Date publication_date;
    private String language;

    public book() {
    }

    public book(int id, String title, String author, Double price, String publisher, String genre, Date publication_date, String language) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.publisher = publisher;
        this.genre = genre;
        this.publication_date = publication_date;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Date getPublication_date() {
        return publication_date;
    }

    public void setPublication_date(Date publication_date) {
        this.publication_date = publication_date;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "book{" + "id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + ", publisher=" + publisher + ", genre=" + genre + ", publication_date=" + publication_date + ", language=" + language + '}';
    }

    
    
}
