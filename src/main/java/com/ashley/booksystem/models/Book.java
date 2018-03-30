package com.ashley.booksystem.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="books")
public class Book {
	@Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String genre;
    private Long numberOfBooksAvailable;
    @Column(updatable=false)
    private Date createdAt;
    private Date updatedAt;
	@OneToMany(mappedBy="book", fetch = FetchType.LAZY)
    private List<CheckedOutBook> checked_books;
	
	public Book () {
		
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

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Long getNumberOfBooksAvailable() {
		return numberOfBooksAvailable;
	}

	public void setNumberOfBooksAvailable(Long numberOfBooksAvailable) {
		this.numberOfBooksAvailable = numberOfBooksAvailable;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<CheckedOutBook> getChecked_books() {
		return checked_books;
	}

	public void setChecked_books(List<CheckedOutBook> checked_books) {
		this.checked_books = checked_books;
	}
}
