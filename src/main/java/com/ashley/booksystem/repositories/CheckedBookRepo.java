package com.ashley.booksystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashley.booksystem.models.CheckedOutBook;

@Repository
public interface CheckedBookRepo extends CrudRepository<CheckedOutBook, Long>{

	@Query(value="SELECT * FROM checked_books WHERE user_id=?1 ORDER BY return_date", nativeQuery=true)
//	@Query("SELECT cb FROM CheckedOutBook cb WHERE cb.id=?1")
	List<CheckedOutBook> findByUserId(Long id);

	@Modifying
	@Query(value="DELETE FROM checked_books WHERE book_id=?1 AND user_id=?2", nativeQuery=true)
	void deleteByBookAndUserId(Long bookID, Long userID);
}
