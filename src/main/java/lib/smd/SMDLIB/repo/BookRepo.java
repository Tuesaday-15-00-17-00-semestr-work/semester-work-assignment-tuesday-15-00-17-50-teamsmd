package lib.smd.SMDLIB.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lib.smd.SMDLIB.model.Book;

@Repository
public class BookRepo {
	/*private List<Book> books = new ArrayList<Book>();
	
	//GET all books
	public List<String> findAllBooks(){
		List<String> namesOfBooks = new ArrayList<String>();
		for(Book b : books) {
			namesOfBooks.add(b.getBookInfo());
		}
		return namesOfBooks;
	}
	
	//GET books by id
	public String findBookByID(int ID) {
		for(Book b : books) {
			if(b.getBookID() == ID) {
				return b.getBookInfo();
			}
		}
		return "No book found";
	}
	
	//POST add book
	public void addNewBook(String bookName, String author, String type) {
		books.add(new Book(books.getLast().getBookID()+1,bookName,author, type));
		System.out.println("User " + bookName + " added!");
	}
	
	//DELETE delete book
	public void deleteBook(int id) {
		books.remove(id-1);
	}
	
	@PostConstruct
	private void init() {
		books.add(new Book(1,"1984", "George Orwell","Roman"));
		books.add(new Book(2,"The old man and the sea","Ernest Hemingway", "Novel"));
	}*/
}
