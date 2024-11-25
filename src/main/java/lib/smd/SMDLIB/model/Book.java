package lib.smd.SMDLIB.model;

public class Book {
	private int id;
	private String bookName, author, type;
	
	public Book(int id, String bookName, String author, String type) {
		this.id = id;
		this.bookName = bookName;
		this.author = author;
		this.type = type;
	}
	
	public int getBookID() {
		return id;
	}
	
	public String getBookName() {
		return bookName;
	}
	
	public String getBookAuthor() {
		return author;
	}
	
	public String getBookType() {
		return type;
	}
	
	public void setBookID(int id) {
		this.id = id;
	}
	
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	
	public void setBookAuthor(String author) {
		this.author = author;
	}
	
	public void setBookType(String type) {
		this.type = type;
	}
	
	public String getBookInfo() {
		String info = "ID: " + id+
					  " Book name: " + bookName+
					  " Author: " + author+
					  " Type: " + type;
		return info;
	}
}
