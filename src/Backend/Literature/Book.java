package Backend.Literature;

public class Book {
	private String bookName, bookAuthor;

	public Book(String bookName, String bookAuthor){
		this.bookName = bookName;
		this.bookAuthor = bookAuthor;
	}

	public void setBookName(String bookName){
		this.bookName = bookName;
	}

	public String getBookName(){
		return bookName;
	}name

	public void setBookAuthor(String bookAuthor){
		this.bookAuthor = bookAuthor;
	}	

	public String getBookAuthor(){
		return bookAuthor;
	}
}
