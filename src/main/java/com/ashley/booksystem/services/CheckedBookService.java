package com.ashley.booksystem.services;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ashley.booksystem.models.CheckedOutBook;
import com.ashley.booksystem.repositories.CheckedBookRepo;

@Service
public class CheckedBookService {
	private CheckedBookRepo checkedBookRepo;
    public CheckedBookService (CheckedBookRepo checkedBookRepo){
        this.checkedBookRepo = checkedBookRepo;
    }
	public List<CheckedOutBook> findByUserId(Long id) {
		return checkedBookRepo.findByUserId(id);
	}
	public void saveCheckedOutBook(@Valid CheckedOutBook checkedoutbook) {
		checkedBookRepo.save(checkedoutbook);
	}
	@Transactional
	public void removeFromUsersCheckedOutBooks(Long bookID, Long userID) {
		checkedBookRepo.deleteByBookAndUserId(bookID, userID);
	}
}
