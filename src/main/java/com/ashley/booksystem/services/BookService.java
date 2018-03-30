package com.ashley.booksystem.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ashley.booksystem.models.Book;
import com.ashley.booksystem.models.User;
import com.ashley.booksystem.repositories.BookRepo;

@Service
public class BookService {
	private BookRepo bookRepo;
    public BookService(BookRepo bookRepo){
        this.bookRepo = bookRepo;
    }
    
	public List<Book> findAllBooks() {
		return bookRepo.findAllWhereAvailableMoreThanOne();
	}

	public List<Book> findByUserId(User id) {
		return bookRepo.findByUserId(id);
	}

	public Book findBookById(Long id) {
		return bookRepo.findById(id).get();
	}

	public void updatedBook(Book book) {
		bookRepo.save(book);
	}

	public List<Book> searchByTextInput(String searchtext) {
		return bookRepo.searchByTextInput(searchtext);
	}

}
