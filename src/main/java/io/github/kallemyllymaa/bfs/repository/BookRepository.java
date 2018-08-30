package io.github.kallemyllymaa.bfs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import io.github.kallemyllymaa.bfs.model.Book;

public interface BookRepository extends CrudRepository<Book, Long> {
	public List<Book> findByNameIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(String name, String author, String description);
}
