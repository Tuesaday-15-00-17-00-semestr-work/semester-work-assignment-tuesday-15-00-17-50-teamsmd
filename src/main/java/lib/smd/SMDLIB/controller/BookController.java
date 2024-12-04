package lib.smd.SMDLIB.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lib.smd.SMDLIB.model.Book;
import lib.smd.SMDLIB.repo.BookRepo;

@RestController
@RequestMapping("/lib/books")
public class BookController {
		
		
		private final BookRepo bookRep;
		
		public BookController(BookRepo bookRep) {
			this.bookRep = bookRep;
		}
		
		@GetMapping("/user/all")
		List<Book> findAllBooks(){
			return bookRep.displayTable();
		}
		
		@GetMapping("/user/{id}")
		Book findBookByID(@PathVariable int id) {
			return bookRep.displayBook(id);
		}
		
		@ResponseStatus(HttpStatus.CREATED)
		@PostMapping("/admin/addbook")
		void createBook(@RequestBody String title, String author, int isbn, int available_copies) {
			bookRep.addBookToDB(title, author, isbn, available_copies);
		}
		
		//idk if you can call it but the update needs to be used only when borrowing or returning book
		@ResponseStatus(HttpStatus.NO_CONTENT)
		@PutMapping("/user/update")
		void updateBook(@RequestBody int id, int change) {
			bookRep.updateBook(id, change);
		}
		
		@ResponseStatus(HttpStatus.GONE)
		@DeleteMapping("/admin/delete")
		void deleteBook(@RequestBody int bookid) {
			bookRep.deleteBookFromDB(bookid);
		}
}