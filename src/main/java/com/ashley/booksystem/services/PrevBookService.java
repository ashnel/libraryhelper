package com.ashley.booksystem.services;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ashley.booksystem.models.BookInCheckOutHistory;
import com.ashley.booksystem.repositories.PrevBookRepo;

@Service
public class PrevBookService {
	private PrevBookRepo prevBookRepo;
    public PrevBookService (PrevBookRepo prevBookRepo){
        this.prevBookRepo = prevBookRepo;
    }
	public void saveCheckedOutBook(BookInCheckOutHistory bookincheckouthistory) {
		prevBookRepo.save(bookincheckouthistory);
	}
}
