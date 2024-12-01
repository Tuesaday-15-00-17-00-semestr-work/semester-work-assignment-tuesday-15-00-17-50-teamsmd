package lib.smd.SMDLIB.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import Database.DatabaseConnection;
import jakarta.annotation.PostConstruct;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.Book;
import lib.smd.SMDLIB.model.User;

@Repository
public class BookRepo {
	
	private List<Book> books;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;
	
	//GET all books
	public List<Book> displayTable() {	
		books = new ArrayList<Book>();
		try {
            DBC.rs = DBC.statement.executeQuery("SELECT * FROM Books;");
		    while(DBC.rs.next()) {		        
		    	books.add(new Book(DBC.rs.getInt("book_id"), DBC.rs.getString("title"),
		        		DBC.rs.getString("author"), DBC.rs.getInt("isbn"), DBC.rs.getInt("available_copies")));
		    }
		    return books;
		}catch(SQLException e){
			System.err.println("Error printing Books table: "+e.getMessage());
		}
		return null;
	}
	
	//GET book by id
	public Book displayBook(int id) {	
		
		String insertSQL = "SELECT * FROM Books WHERE book_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            
            DBC.rs = statement.executeQuery();
            
			Book recBook =  new Book(DBC.rs.getInt("book_id"), DBC.rs.getString("title"),
	        		DBC.rs.getString("author"), DBC.rs.getInt("isbn"), DBC.rs.getInt("available_copies"));
			
		    return recBook;
		}catch(SQLException e){
			System.err.println("Error printing Book: "+e.getMessage());
		}
		return null;
	}
	
	//POST add book
	public static void addBookToDB(String title, String author, int isbn, int available_copies){
		String insertSQL = "INSERT INTO Books(title, author, isbn, available_copies) VALUES(?,?,?,?);";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, isbn);
            statement.setInt(4, available_copies);
			
			statement.executeUpdate();
			System.out.println("Title " +title+" added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	//PUT update book count
	public void updateBook(int id, int change) {
		String insertSQL;
		int newCount;
		
		try {	
			insertSQL = "SELECT * FROM Books WHERE book_id=?;";
			PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            DBC.rs = statement.executeQuery();
            newCount = DBC.rs.getInt("available_copies");
            
            insertSQL = "UPDATE Books SET available_copies=? WHERE book_id=?;";
            statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, newCount+change);
            statement.setInt(2, id);
            
            statement.executeUpdate();
			
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	//DELETE delete book
	public static void deleteBookFromDB(int id){
		
		String insertSQL = "DELETE FROM Books WHERE book_id=?;";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			System.out.println("Book removed!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
}
