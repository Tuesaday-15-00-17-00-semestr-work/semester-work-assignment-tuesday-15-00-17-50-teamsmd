package lib.smd.SMDLIB.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import Database.DatabaseConnection;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.Transaction;

@Repository
public class TransactionRepo {
	private List<Transaction> transactions;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;
	
	private final BookRepo bookRep;
	
	public TransactionRepo(BookRepo bookRep) {
		this.bookRep = bookRep;
	}

//--------------------------------------GET all transactions----------------------------------------------|
	public List<Transaction> displayTable() {
		transactions = new ArrayList<Transaction>();
		
		try {
            DBC.rs = DBC.statement.executeQuery("SELECT Borrows.transaction_id, Users.username, Books.title, "
            		+ "Borrows.action, Borrows.date FROM Borrows "
            		+ "JOIN Users ON (Borrows.user_id = Users.user_id) "
            		+ "JOIN Books ON (Borrows.book_id = Books.book_id)");
		    while(DBC.rs.next()) {		        
		    	transactions.add(new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getString("username"),
		        		DBC.rs.getString("title"), DBC.rs.getString("action"), DBC.rs.getString("date")));
		    }
		    return transactions;
		}catch(SQLException e){
			log.info("Error printing Transaction table: "+e.getMessage());
		}
		return null;
	}
	
//--------------------------------------GET transaction by id----------------------------------------------|
	public Transaction displayTransaction(int id) {	
		
		String insertSQL = "SELECT Borrows.transaction_id, Users.username, Books.title, "
        		+ "Borrows.action, Borrows.date FROM Borrows "
        		+ "JOIN Users ON (Borrows.user_id = Users.user_id) "
        		+ "JOIN Books ON (Borrows.book_id = Books.book_id) "
        		+ "WHERE transaction_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            
            DBC.rs = statement.executeQuery();
            
            Transaction recTrans =  new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getString("username"),
	        		DBC.rs.getString("title"), DBC.rs.getString("action"), DBC.rs.getString("date"));
			
		    return recTrans;
		}catch(SQLException e){
			log.info("Error printing Transaction: "+e.getMessage());
		}
		return null;
	}
	
//--------------------------------------GET transactions for user------------------------------------------|
	public List<Transaction> displayTransactionForUser(int user_id) {	
			
		String insertSQL = "SELECT Borrows.transaction_id, Users.username, Books.title, "
        		+ "Borrows.action, Borrows.date FROM Borrows "
        		+ "JOIN Users ON (Borrows.user_id = Users.user_id) "
        		+ "JOIN Books ON (Borrows.book_id = Books.book_id) "
        		+ "WHERE user_id=?;";
		transactions = new ArrayList<Transaction>();
		
		try {
			PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
	        statement.setInt(1, user_id);
	            
	        DBC.rs = statement.executeQuery();
			
	        while(DBC.rs.next()) {		        
		    	transactions.add(new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getString("usename"),
		        		DBC.rs.getString("title"), DBC.rs.getString("action"), DBC.rs.getString("date")));
		    }
	        
	        return transactions;
		}catch(SQLException e){
			log.info("Error printing Transaction: "+e.getMessage());
		}
		return null;
	}
	
//--------------------------------------POST add transaction-----------------------------------------------|
	public void addTransActionToDB(int user_id, int book_id, String action, String date){
		String insertSQL = "INSERT INTO Borrows(user_id, book_id, action, date) VALUES(?,?,?,?);";
		
		try {				
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, user_id);
            statement.setInt(2, book_id);
            statement.setString(3, action);
            statement.setString(4, date);
            
			statement.executeUpdate();
			
			int change = 0;
			
			if(action.equals("Borrow")) {
				change = -1;
			}else if(action.equals("Return")) {
				change = 1;
			}else {
				throw new SQLException();
			}
			
			bookRep.updateBook(book_id, change);
			
			log.info("Transaction action: " +action);
		}catch(SQLException e){
			log.info("Error adding transaction: "+e.getMessage());
		}
	}

//--------------------------------------DELETE delete transaction-----------------------------------------|
	public void deleteTransFromDB(int id){
		
		String insertSQL = "DELETE FROM Borrows WHERE transaction_id=?;";
		
		try {		
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			log.info("Transaction number: "+ id +" removed!");
		}catch(SQLException e){
			log.info("Error delete transaction: "+e.getMessage());
		}
	}
}
