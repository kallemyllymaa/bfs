package io.github.kallemyllymaa.bfs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import io.github.kallemyllymaa.bfs.model.Book;
import io.github.kallemyllymaa.bfs.service.BookService;

@Controller
public class BookController {
	
	@Autowired
	BookService bookService;

	@GetMapping("/")
	public String home() {
		return "books";
	}

	@PostMapping("/")
	public RedirectView createBook(@ModelAttribute Book book) {
		bookService.save(book);
		return new RedirectView("/");
	}

	@PutMapping("/{id}")
	public RedirectView modifyBook(@ModelAttribute Book newBook, @PathVariable Long id) {
		bookService.modify(newBook, id);
		return new RedirectView("/");
	}

	@DeleteMapping("/{id}")
	public RedirectView deleteBook(@PathVariable Long id) {
		bookService.deleteById(id);
		return new RedirectView("/");
	}

	@GetMapping(value = { "/{id}", "new" })
	public ModelAndView addOrEditBook(@PathVariable(name = "id", required = false) Long id) {
		ModelAndView mav = new ModelAndView();
		if (id != null) {
			return bookService.findById(id).map(book -> {
				mav.addObject("id", id);
				mav.addObject("book", book);
				mav.setViewName("addOrEdit");
				return mav;
			}).orElseGet(() -> {
				mav.setViewName("notFound");
				return mav;
			});
		} else {
			mav.addObject("book", new Book());
			mav.setViewName("addOrEdit");
			return mav;
		}
	}
	
	@GetMapping("/api/list")
	@ResponseBody
	public Iterable<Book> listBooks() {
		return bookService.findAll();
	}

	@GetMapping("/api/search")
	@ResponseBody
	public List<Book> searchBooks(@RequestParam String query) {
		return bookService.searchAllFields(query);
	}
}
