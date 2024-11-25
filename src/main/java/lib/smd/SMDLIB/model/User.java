package lib.smd.SMDLIB.model;

public class User {
	private int id, booksLeased;
	private String fname, lname, email, pass;
	
	public User(int id, String fname, String lname, String email, String pass) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pass = pass;
	}
	
	public void setUserID(int id) {
		this.id = id;
	}
	
	public void setUserName(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
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
		String userFullName = fname + " " + lname;
		return userFullName;
	}
	
	public String getUserEmail() {
		return email;
	}
	
	public String getUserPass() {
		return pass;
	}
	
	public String getUserInfo() {
		String info = "ID: " + id+
					  " First Name: " + fname+
					  " Last Name: " + lname+
					  " Email: " + email;
		return info;
	}
}
