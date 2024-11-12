package Backend;

import java.util.ArrayList;

import Backend.Groups.*;

public class MainServer {
	//temp arraylists to store the users for testing
	private ArrayList<User> DBUser;
	private ArrayList<Admin> ABAdmin;
	
	//login
	public int AAA(int ID, String pass) {
		//check the DB for the correct pass for the id
		//ak toto citas simon tak skus zavolat tuto funkciu pri logine
		/*
		 0 - neuspesne prihlasenie, zle id alebo heslo alebo neregistrovany
		 1 - uspesne prihlasenie
		 2 - chybajuce id
		 3 - chybajuce heslo
		*/
		
		if(ID == -1) {
			return 2;
		}
		
		if(pass.equals(null)) {
			return 3;
		}
		
		for(User n : DBUser) {
			
			if((n.getUserID() == ID)) {
				if(n.getUserPass().equals(pass) == true) {
					System.out.println("Login succsess!");
					return 1;
				}else {
					System.out.println("Wrong password!");
					return 0;
				}
				
			}
		}
		
		return 0;
	}
	
	//register
	public void registerUser(String name, String pass) {
		User newUser = new User((DBUser.getLast().getUserID()+1), name);
		DBUser.add(newUser);
	}
	
}