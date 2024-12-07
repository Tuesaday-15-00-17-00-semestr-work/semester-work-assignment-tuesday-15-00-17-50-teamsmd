package lib.smd.SMDLIB.repo;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import Database.DatabaseConnection;
import lib.smd.SMDLIB.SmdlibApplication;
import lib.smd.SMDLIB.model.UserEntity;

@Repository
public class UserRepo {
	
	private List<UserEntity> users;
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;

//--------------------------------------GET all users----------------------------------------------------|
	public List<UserEntity> displayTable() {
		users = new ArrayList<UserEntity>();
		try {
            DBC.rs = DBC.statement.executeQuery("SELECT Users.user_id,"
            		+ "Users.username, Users.password, Roles.role_name, Users.email FROM Users JOIN Roles "
            		+ "ON (Users.role_id = Roles.role_id)");
		    while(DBC.rs.next()) {		        
		        users.add(new UserEntity(DBC.rs.getInt("user_id"), DBC.rs.getString("username"),
		        		DBC.rs.getString("password"), DBC.rs.getString("role_name"), DBC.rs.getString("email")));
		    }
		    return users;
		}catch(SQLException e){
			log.info("Error printing Users table: "+e.getMessage());
		}
		return null;
	}
	
//--------------------------------------GET users by email----------------------------------------------------|
		public UserEntity returnUserByEmail(String email) {	
			
			String insertSQL = "SELECT Users.user_id,"
            		+ "Users.username, Users.password, Roles.role_name, Users.email FROM Users JOIN Roles "
            		+ "ON (Users.role_id = Roles.role_id) WHERE email=?;";
			
			try {
	            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
	            statement.setString(1, email);
	            
	            DBC.rs = statement.executeQuery();
	            
				UserEntity recUser =  new UserEntity(DBC.rs.getInt("user_id"), DBC.rs.getString("username"),
		        		DBC.rs.getString("password"), DBC.rs.getString("role_name"), DBC.rs.getString("email"));
				
			    return recUser;
			}catch(SQLException e){
				log.info("User "+ email +" exists!");
				return null;
			}
		}
//--------------------------------------GET users by id----------------------------------------------------|	
	public UserEntity displayUser(int id) {	
		
		String insertSQL = "SELECT Users.user_id,"
        		+ "Users.username, Users.password, Roles.role_name, Users.email FROM Users JOIN Roles "
        		+ "ON (Users.role_id = Roles.role_id) WHERE user_id=?;";
		
		try {
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
            
            DBC.rs = statement.executeQuery();
            
			UserEntity recUser =  new UserEntity(DBC.rs.getInt("user_id"), DBC.rs.getString("username"),
	        		DBC.rs.getString("password"), DBC.rs.getString("role_name"), DBC.rs.getString("email"));
			
		    return recUser;
		}catch(SQLException e){
			log.info("Error printing Users: "+e.getMessage());
		}
		return null;
	}
//--------------------------------------POST add user----------------------------------------------------|	
	public void addUserToDB(String username, String password, int role, String email){
		String insertSQL = "INSERT INTO Users(username, password, role_id, email) VALUES(?,?,?,?);";
		
		try {			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, role);
            statement.setString(4, email);
			
			statement.executeUpdate();
			log.info("User " +username+" added!");
		}catch(SQLException e){
			log.info("Error adding user: "+e.getMessage());
		}
	}
	
//--------------------------------------DELETE delete user----------------------------------------------------|
	public void deleteUserFromDB(int id){
		
		String insertSQL = "DELETE FROM Users WHERE user_id=?;";
		
		try {			
            PreparedStatement statement = DBC.connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			log.info("User "+ id +" removed!");
		}catch(SQLException e){
			log.info("Error deleting user: "+e.getMessage());
		}
	}
}
