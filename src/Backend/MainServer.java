package Backend;

import java.util.ArrayList;

import Backend.Groups.*;

public class MainServer {
	//temp arraylists to store the users for testing
	private ArrayList<User> DBUser;
	private ArrayList<Admin> ABAdmin;
	
	public int AAA(int ID, String pass) {
		//check the DB for the correct pass for the id
		//ak toto citas simon tak skus zavolat tuto funkciu pri logine
		/*
		 0 - neuspesne prihlasenie, zle meno alebo heslo
		 1 - uspesne prihlasenie
		 2 - chybajuce meno
		 3 - chybajuce heslo
		 4 - neregistrovany uzivatel, prosim zaregistruj sa
		*/
		return 1;
	}
	
}