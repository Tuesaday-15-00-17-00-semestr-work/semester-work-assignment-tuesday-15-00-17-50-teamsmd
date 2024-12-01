package Backend.Groups;

import Backend.Literature.*;
import java.util.*;

public class User{
	protected int ID;
	protected String User_name;
	protected String User_Pass;
	protected ArrayList<Book> User_added_books;
	
	public User(int ID, String User_name) {
		this.ID = ID;
		this.User_name = User_name;
		User_added_books = new ArrayList<Book>();
	}

	/*public Book bookLookUp(Book toLookFor){
		for(Book b : )
	}*/
	
	public void addBook(Book newBook) {
		for(Book b : User_added_books) {
			if(b.getBookName().equals(newBook.getBookName()) == true){
				System.out.println("Book aleady exists!");
				return;
			}
		}
		User_added_books.add(newBook);
		System.out.println("Book added!");
	}

	public void viewBook(Book theBook){
		for(Book b : User_added_books) {
			if(b.getBookName().equals(theBook.getBookName()) == true){
				//open pdf in REST API spring boot web browser (just open the PDF file)	
			}
		}
		System.out.println("Cant find the book!");
	}

	public void renameUser(String New_name) {
		User_name = New_name;
	}
	
	public void changeUserID(int New_ID) {
		ID = New_ID;
	}
	
	public void changeUserPass(String newPass) {
		User_Pass = newPass;
	}
	
	public String getUserName() {
		return User_name;
	}
	
	public int getUserID() {
		return ID;
	}
	
	public String getUserPass() {
		return User_Pass;
	}
}
