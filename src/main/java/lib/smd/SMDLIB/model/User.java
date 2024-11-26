package lib.smd.SMDLIB.model;

public class User {
	private int id, booksLeased, role_id;
	private String username, email, pass;
	
	public User(int id, String username, String email, String pass) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.pass = pass;
	}
	
	public void setUserID(int id) {
		this.id = id;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public void setUserEmail(String email) {
		this.email = email;
	}
	
	public void setUserPass(String pass) {
		this.pass = pass;
	}
	
	public int getUserID() {
		return id;
	}
	
	public String getUserName() {
		return username;
	}
	
	public String getUserEmail() {
		return email;
	}
	
	public String getUserPass() {
		return pass;
	}
	
	public String getUserInfo() {
		String info = "ID: " + id+
					  " First Name: " +username+
					  " Email: " + email;
		return info;
	}
}
