package com.ashley.booksystem.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ashley.booksystem.models.User;

public interface UserRepo extends CrudRepository<User, Long> {
	User findByUsername(String username);
}
