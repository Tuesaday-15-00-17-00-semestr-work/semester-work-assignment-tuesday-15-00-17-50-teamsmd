package lib.smd.SMDLIB.repo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;
import lib.smd.SMDLIB.model.User;

@Repository
public class UserRepo {
	private List<User> users = new ArrayList<User>();
	
	public List<String> findAllUsers(){
		List<String> namesOfUsers = new ArrayList<String>();
		for(User u : users) {
			namesOfUsers.add(u.getUserInfo());
		}
		return namesOfUsers;
	}
	
	public String findByID(int ID) {
		for(User u : users) {
			if(u.getUserID() == ID) {
				return u.getUserInfo();
			}
		}
		return "No User found";
	}
	
	@PostConstruct
	private void init() {
		users.add(new User(1,"John","Smith", "john@mail.com","123"));
		users.add(new User(2,"Peter","Griffin", "peter@mail.com","321"));
	}
}
