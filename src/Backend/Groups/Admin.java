package Backend.Groups;

import java.util.ArrayList;
import java.sql.*;

public final class Admin extends User{
	
	public Admin(int ID, String User_name) {
		super(ID, User_name);
	}
	
	public void addUsers(ArrayList<User> list,User newUser) {
		/*for(User n : list) {
			if(n.getUserID() == newUser.getUserID()) {
				System.out.println("User already exists!");
				return;
			}
		}
		
		list.add(newUser);*/
		System.out.println("User " + newUser.getUserName() + " with ID: " + newUser.getUserID() + 
				" has been created and added to the database.");
	}
	
	public void removeUser(ArrayList<User> list,User delUser) {
		/*for(User n : list) {
			if(n.getUserID() == delUser.getUserID()) {
				list.remove(delUser);
				System.out.println("User deleted.");
				return;
			}
		}*/
		System.out.println("User does not exist!");
	}
}
