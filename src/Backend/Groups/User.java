package Backend.Groups;

public class User{
	protected int ID;
	protected String User_name;
	
	public User(int ID, String User_name) {
		this.ID = ID;
		this.User_name = User_name;
	}
	
	public void renameUser(String New_name) {
		User_name = New_name;
	}
	
	public void changeUserID(int New_ID) {
		ID = New_ID;
	}
	
	public String getUserName() {
		return User_name;
	}
	
	public int getUserID() {
		return ID;
	}
}
