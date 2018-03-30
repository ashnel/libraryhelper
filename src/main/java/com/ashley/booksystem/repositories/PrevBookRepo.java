package com.ashley.booksystem.repositories;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ashley.booksystem.models.BookInCheckOutHistory;

@Repository
public interface PrevBookRepo extends CrudRepository<BookInCheckOutHistory, Long>{

}
