package com.ashley.booksystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashley.booksystem.models.Book;
import com.ashley.booksystem.models.User;


@Repository
public interface BookRepo extends CrudRepository<Book, Long> {
	List<Book> findAll();

//	checked_books
	@Query("SELECT b FROM Book b JOIN b.checked_books cb WHERE cb.user = ?1 ORDER BY cb.returnDate")
	List<Book> findByUserId(User id);

	@Query("SELECT b FROM Book b WHERE numberOfBooksAvailable > 0 ORDER BY b.title ASC")
	List<Book> findAllWhereAvailableMoreThanOne();

	@Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.author LIKE %?1% OR b.genre LIKE %?1% ORDER BY b.title ASC")
	List<Book> searchByTextInput(String searchtext);
}
