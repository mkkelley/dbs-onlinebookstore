package net.minthe.dbsbookshop.book;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Created by Michael Kelley on 10/14/2018
 *
 * Domain object
 */
@Entity
@Table(name="books")
public class Book {
    @Id
    private String isbn;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private String subject;

    public Book(String isbn, String author, String title, BigDecimal price, String subject) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.price = price;
        this.subject = subject;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", subject='" + subject + '\'' +
                '}';
    }

    public Book() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        if (isbn != null ? !isbn.equals(book.isbn) : book.isbn != null) return false;
        if (!author.equals(book.author)) return false;
        if (!title.equals(book.title)) return false;
        if (!price.equals(book.price)) return false;
        return subject.equals(book.subject);
    }

    @Override
    public int hashCode() {
        int result = isbn != null ? isbn.hashCode() : 0;
        result = 31 * result + author.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + price.hashCode();
        result = 31 * result + subject.hashCode();
        return result;
    }
}
