package lib.smd.SMDLIB.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import Database.DatabaseConnection;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.Book;
import lib.smd.SMDLIB.model.Transaction;
import lib.smd.SMDLIB.model.UserEntity;

@Repository
public class BookRepo {
	
	private List<Book> books;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;
	
	private final UserRepo userRep;
		
	public BookRepo(UserRepo userRep, PasswordEncoder passEnc) {
		this.userRep = userRep;
	}
	
//--------------------------------------GET all books----------------------------------------------------|
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
			log.info("Error printing Books table: "+e.getMessage());
		}
		return null;
	}

//--------------------------------------GET book by id----------------------------------------------------|
	public Book displayBook(String bok) {	
		
		String insertSQL = "SELECT * FROM Books WHERE title=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setString(1, bok);
            
            DBC.rs = statement.executeQuery();
            
			Book recBook =  new Book(DBC.rs.getInt("book_id"), DBC.rs.getString("title"),
	        		DBC.rs.getString("author"), DBC.rs.getInt("isbn"), DBC.rs.getInt("available_copies"));
			
		    return recBook;
		}catch(SQLException e){
			log.info("Error printing Book: "+e.getMessage());
		}
		return null;
	}
	
//--------------------------------------POST add book----------------------------------------------------|	
	public void addBookToDB(String title, String author, int isbn, int available_copies){
		String insertSQL = "INSERT INTO Books(title, author, isbn, available_copies) VALUES(?,?,?,?);";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, isbn);
            statement.setInt(4, available_copies);
			
			statement.executeUpdate();
			log.info("Title " +title+" added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}

//--------------------------------------PUT update book count----------------------------------------------------|	
	public void updateBook(int id, int change) {
		String insertSQL;
		int newCount;
		
		try {	
			insertSQL = "SELECT * FROM Books WHERE book_id=?;";
			PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            DBC.rs = statement.executeQuery();
            newCount = DBC.rs.getInt("available_copies");
            
            //find if the available copies are not negative
            if(newCount+change < 0) {
            	log.info("NOT ENOUGH COPIES!!");
            	return;
            }
            
            insertSQL = "UPDATE Books SET available_copies=? WHERE book_id=?;";
            statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, newCount+change);
            statement.setInt(2, id);
            
            statement.executeUpdate();
            log.info(id + " -book has been updated!");		
		}catch(Exception e){
			log.info("Error updating book: "+e.getMessage());
		}
	}
	
//--------------------------------------DELETE delete book----------------------------------------------------|
	public void deleteBookFromDB(int id){
		
		String insertSQL = "DELETE FROM Books WHERE book_id=?;";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			log.info(id + " -book removed!");
		}catch(SQLException e){
			log.info("Error deleting book: "+e.getMessage());
		}
	}
	
	//--------------------my books------------------------------
	public List<Book> myBooks(String username) {	
		books = new ArrayList<Book>();
		List<Transaction> transactions = new ArrayList<Transaction>();
		int userID = findUserID(username);
		String insertSQL = "SELECT Borrows.transaction_id, Users.email, Books.title, "
        		+ "Borrows.action, Borrows.date FROM Borrows "
        		+ "JOIN Users ON (Borrows.user_id = Users.user_id) "
        		+ "JOIN Books ON (Borrows.book_id = Books.book_id)"
        		+ "WHERE user_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, userID);
            
            DBC.rs = statement.executeQuery();
            
            while(DBC.rs.next()) {		        
		    	transactions.add(new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getString("email"),
		        		DBC.rs.getString("title"), DBC.rs.getString("action"), DBC.rs.getString("date")));
		    }
            
		    return getBooks(transactions, books);
		}catch(SQLException e){
			log.info("Error printing Books: "+e.getMessage());
		}
		return null;
	}
	
	private int findUserID(String username) {
		UserEntity user = userRep.returnUserByEmail(username);
		System.out.println(username);
		return user.user_id();
	}
	
	private List<Book> getBooks(List<Transaction> tr, List<Book> books) {
		int dlz = tr.size();
		int poz;
		List<Book> contain = new ArrayList<Book>();
		for(int i = 0; i < dlz; i++) {
			poz = dlz-i;
			if(tr.get(poz).action().equals("Borrow")) {
				if(books.contains(tr.get(poz)) == false) {
					if(contain.contains(tr.get(poz))) {
						books.add(displayBook(tr.get(poz).book_id()));
						i = 0;
					}
				}
			}else if(tr.get(poz).action().equals("Return")) {
				if(books.contains(tr.get(poz)) == true) {
					books.remove(displayBook(tr.get(poz).book_id()));
					i = 0;
				}
			}
		}
		return books;
	}
}
