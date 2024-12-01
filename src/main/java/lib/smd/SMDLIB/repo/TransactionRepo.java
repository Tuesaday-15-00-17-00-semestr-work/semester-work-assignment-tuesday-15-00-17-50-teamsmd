package lib.smd.SMDLIB.repo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Database.DatabaseConnection;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepo {
private List<Transaction> transactions = new ArrayList<Transaction>();
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;

	//GET all transactions
	public List<Transaction> displayTable() {	
		try {
            DBC.rs = DBC.statement.executeQuery("SELECT * FROM Borrows;");
		    while(DBC.rs.next()) {		        
		    	transactions.add(new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getInt("user_id"),
		        		DBC.rs.getInt("book_id"), DBC.rs.getString("action"), DBC.rs.getString("date")));
		    }
		    return transactions;
		}catch(SQLException e){
			System.err.println("Error printing Transaction table: "+e.getMessage());
		}
		return null;
	}
	
	//GET transaction by id
	public Transaction displayTransaction(int id) {	
		
		String insertSQL = "SELECT * FROM Borrows WHERE transaction_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            
            DBC.rs = statement.executeQuery();
            
            Transaction recTrans =  new Transaction(DBC.rs.getInt("transaction_id"), DBC.rs.getInt("user_id"),
	        		DBC.rs.getInt("book_id"), DBC.rs.getString("action"), DBC.rs.getString("date"));
			
		    return recTrans;
		}catch(SQLException e){
			System.err.println("Error printing Transaction: "+e.getMessage());
		}
		return null;
	}
	
	//POST add transaction
	public static void addTransActionToDB(int transaction_id, int user_id, int book_id, String action, String date){
		String insertSQL = "INSERT INTO Borrows(transaction_id, user_id, book_id, action, date) VALUES(?,?,?,?,?);";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, transaction_id);
            statement.setInt(2, user_id);
            statement.setInt(3, book_id);
            statement.setString(4, action);
            statement.setString(5, date);
            
			statement.executeUpdate();
			System.out.println("Transaction number " +transaction_id+" added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	//DELETE delete transaction
	public static void deleteTransFromDB(int id){
		
		String insertSQL = "DELETE FROM Borrows WHERE transaction_id=?;";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			System.out.println("Transaction removed!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
}
