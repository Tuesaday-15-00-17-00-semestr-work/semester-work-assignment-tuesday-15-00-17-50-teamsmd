package lib.smd.SMDLIB.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.model.Book;
import lib.smd.SMDLIB.repo.BookRepo;

@RestController
@RequestMapping("/api/books")
public class BookController {
		
		
		private final BookRepo bookRep;
		
		public BookController(BookRepo bookRep) {
			this.bookRep = bookRep;
		}
		
		@GetMapping("")
		List<String> findAllBooks(){
			return bookRep.findAllBooks();
		}
		
		@GetMapping("/{id}")
		String findBookByID(@PathVariable int id) {
			return bookRep.findBookByID(id);
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("")
		void createBook(@RequestBody String name, String author, String type) {
			bookRep.addNewBook(name, author, type);
		}
		
}