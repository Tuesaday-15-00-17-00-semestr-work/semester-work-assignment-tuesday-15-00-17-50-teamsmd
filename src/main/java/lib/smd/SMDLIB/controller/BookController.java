package lib.smd.SMDLIB.controller;

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

import lib.smd.SMDLIB.Dto.BookD.BookDelDto;
import lib.smd.SMDLIB.Dto.BookD.BookDto;
import lib.smd.SMDLIB.Dto.BookD.BookUpdateDto;
import lib.smd.SMDLIB.model.Book;
import lib.smd.SMDLIB.repo.BookRepo;

@RestController
@RequestMapping("/lib/books")
public class BookController {
				
	private final BookRepo bookRep;
		
	public BookController(BookRepo bookRep) {
		this.bookRep = bookRep;
	}

//--------------------------------------GET----------------------------------------|
	@GetMapping("/user/all")
	List<Book> findAllBooks(){
		return bookRep.displayTable();
	}
		
	@GetMapping("/user/{id}")
	Book findBookByID(@PathVariable int id) {
		return bookRep.displayBook(id);
	}
		
//-------------------------------------POST----------------------------------------|
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/user/addbook")
	void createBook(@RequestBody BookDto jsonBook) {
		bookRep.addBookToDB(jsonBook.title, jsonBook.author, jsonBook.isbn, jsonBook.available_copies);
	}

//-------------------------------------PUT-----------------------------------------|
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PutMapping("/user/update")
	void updateBook(@RequestBody BookUpdateDto jsonBU) {
		bookRep.updateBook(jsonBU.id, jsonBU.change);
	}

//----------------------------------DELETE-----------------------------------------|		
	@ResponseStatus(HttpStatus.GONE)
	@DeleteMapping("/admin/delete/{id}")
	void deleteBook(@PathVariable int id) {
		bookRep.deleteBookFromDB(id);
	}
}