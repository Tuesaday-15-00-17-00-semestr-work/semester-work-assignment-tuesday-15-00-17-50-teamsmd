package lib.smd.SMDLIB.model;

public class User {
	private int id;
	private String fname, lname, email, pass;
	
	public User(int id, String fname, String lname, String email, String pass) {
		this.id = id;
		this.fname = fname;
		this.lname = lname;
		this.email = email;
		this.pass = pass;
	}
	
	public String getUserName() {
		return fname;
	}
	
	public void setUserPass(String newPass) {
		pass = newPass;
	}
	
	public int getUserID() {
		return id;
	}
	
	public String getUserInfo() {
		String info = "ID: " + id+
					  " First Name: " + fname+
					  " Last Name: " + lname+
					  " Email: " + email;
		return info;
	}
}
