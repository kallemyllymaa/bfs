package io.github.kallemyllymaa.bfs.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.kallemyllymaa.bfs.model.Book;
import io.github.kallemyllymaa.bfs.repository.BookRepository;

@Service
public class BookService {
	
	@Autowired
	BookRepository bookRepository;
	
	public Book save(Book book) {
		return bookRepository.save(book);
	};
	
	public Book modify(Book newBook, Long id) {
		return bookRepository.findById(id).map(book -> {
			book.setAuthor(newBook.getAuthor());
			book.setName(newBook.getName());
			book.setDescription(newBook.getDescription());
			return bookRepository.save(book);
		}).orElseGet(() -> {
			newBook.setId(id);
			return bookRepository.save(newBook);
		});
	};
	
	public void deleteById(Long id) {
		bookRepository.deleteById(id);
	}
	
	public Optional<Book> findById(Long id) {
		return bookRepository.findById(id);
	}
	
	public Iterable<Book> findAll() {
		return bookRepository.findAll();
	}
	
	public List<Book> searchAllFields(String query) {
		return bookRepository.findByNameIgnoreCaseContainingOrAuthorIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(query, query, query);
	}

}
