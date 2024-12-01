package Backend.Database;

import java.sql.*;

public class DBAccess {

	ResultSet rs;
	Statement statement;

	public static void main(String[] args) {

		Connection connection = null;
	
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:LIBDB.db");
			statement = connection.createStatement();
			statement.setQueryTimeout(30);

			//ak chcem pridat do sql DB tak treba pouzit statement.executeUpdate(prikaz); lol
			statement.executeUpdate("");

			rs = statement.executeQuery("SELECT * FROM Users;");
		    while(rs.next()) {
		    	System.out.println("ID : " + rs.getString("ID"));
		        System.out.println("Name : " + rs.getString("f_name"));
		        System.out.println("Lastname : " + rs.getString("l_name"));
		        System.out.println("Books uploaded : " + rs.getString("booksuploaded") + "\n");
		    }
		}catch(SQLException e) {
			System.err.println(e.getMessage());
		}
		
	}
	
	private void addUserToDB(String fname, String lname, String pass, int booksUpLoad){
		try {
			statement.executeUpdate("INSERT INTO Users VALUES("+fname+","+lname+","+pass+","+booksUpLoad+");");
			System.printf("User added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}

}
