package lib.smd.SMDLIB.repo;

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
import lib.smd.SMDLIB.model.UserR;

@Repository
public class UserRepo {
	
	private List<User> users = new ArrayList<User>();
	
	private static final Logger log = LoggerFactory.getLogger(SmdlibApplication.class);
	private static DatabaseConnection DBC;
	
	//GET all users
	public List<String> findAllUsers(){
		return DBC.displayTable("Users");
	}
	
	//GET users by id
	public String findUserByID(int ID) {
		for(User u : users) {
			if(u.getUserID() == ID) {
				return u.getUserInfo();
			}
		}
		return "No User found";
	}
	
	//POST add user
	public void addNewUser(String username, String email, String pass) {
		users.add(new User(users.getLast().getUserID()+1,username, email,pass));
		System.out.println("User " + username + " added!");
	}
	
	//DELETE delete user
	public void deleteUser(int id) {
		users.remove(id-1);
	}
	
	@PostConstruct
	private void init() {
		users.add(new User(1,"JohnSmith", "john@mail.com","123"));
		users.add(new User(2,"PeterGriffin", "peter@mail.com","321"));
	}
}
