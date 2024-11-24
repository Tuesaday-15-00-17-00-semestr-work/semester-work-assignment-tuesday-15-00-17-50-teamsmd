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
}
