package lib.smd.SMDLIB.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import Database.DatabaseConnection;
import jakarta.annotation.PostConstruct;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.User;

@Repository
public class UserRepo {
	
	private List<User> users = new ArrayList<User>();
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;
	
	//GET all users
	public List<User> displayTable() {	
		try {
            DBC.rs = DBC.statement.executeQuery("SELECT * FROM Users;");
		    while(DBC.rs.next()) {		        
		        users.add(new User(DBC.rs.getInt("user_id"), DBC.rs.getString("username"),
		        		DBC.rs.getString("password"), DBC.rs.getInt("role_id"), DBC.rs.getString("email")));
		    }
		    return users;
		}catch(SQLException e){
			System.err.println("Error printing Users table: "+e.getMessage());
		}
		return null;
	}
	
	//GET users by id
	public User displayUser(int id) {	
		
		String insertSQL = "SELECT * FROM Users WHERE user_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            
            DBC.rs = statement.executeQuery();
            
			User recUser =  new User(DBC.rs.getInt("user_id"), DBC.rs.getString("username"),
	        		DBC.rs.getString("password"), DBC.rs.getInt("role_id"), DBC.rs.getString("email"));
			
		    return recUser;
		}catch(SQLException e){
			System.err.println("Error printing Users: "+e.getMessage());
		}
		return null;
	}
	
	//POST add user
	public static void addUserToDB(String username, String password, int role, String email){
		String insertSQL = "INSERT INTO Users(username, password, role_id, email) VALUES(?,?,?,?);";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, role);
            statement.setString(4, email);
			
			statement.executeUpdate();
			System.out.println("User " +username+" added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	//DELETE delete user
	public static void deleteUserFromDB(int id){
		
		String insertSQL = "DELETE FROM Users WHERE ID=?;";
		
		try {	
			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			System.out.println("User removed!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	@PostConstruct
	private void init() {
		//users.add(new User(1,"JohnSmith", "john@mail.com","123"));
		//users.add(new User(2,"PeterGriffin", "peter@mail.com","321"));
	}
}
