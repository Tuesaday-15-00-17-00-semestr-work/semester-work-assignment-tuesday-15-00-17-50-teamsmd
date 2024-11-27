package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
	static ResultSet rs;
	public static Statement statement;
	public static Connection connection = null;
	private static String pathToDB = "src/main/java/Database/LIBDB.db";
	
	//CALL IN MAIN TO CONNECT TO DB
	public static String connectToDB() {
		try {
			//sleduje od .git, takze tam kde je src/
			connection = DriverManager.getConnection("jdbc:sqlite:"+pathToDB);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);
			return "Database connected!";

			//ak chcem pridat do sql DB tak treba pouzit statement.executeUpdate(prikaz); lol
			//statement.executeUpdate("");
		}catch(SQLException e) {
			System.err.println(e.getMessage());
			return "SQLite connection failed: " + e;
		}
	}
	
	public static List<String> displayTable(String tableName) { //prerobit vsetko na vlastne
		String insertSQL = "SELECT * FROM "+tableName+";";
		
		try {
			List<String> out = new ArrayList<String>();
			String con;
            rs = statement.executeQuery(insertSQL);
		    while(rs.next()) {
		    	out.add("ID: " + rs.getString("user_id"));
		        out.add("Username: " + rs.getString("username"));
		        out.add("E-mail: " + rs.getString("email"));
		        out.add("Role: " + rs.getString("role_id") + "\n\n");
		    }
		    return out;
		}catch(SQLException e){
			System.err.println("Error printing "+tableName+"table: "+e.getMessage());
		}
		return null;
	}
//-----------------------TABLE: Users-------------------------|
	public static void addUserToDB(String username, String password, int role, String email){
		String insertSQL = "INSERT INTO Users(username, password, role_id, email) VALUES(?,?,?,?);";
		
		try {	
			
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, role);
            statement.setString(4, email);
			
			statement.executeUpdate();
			System.out.println("User " +username+" added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}
	
	public static void deleteUserFromDB(int id){
		
		String insertSQL = "DELETE FROM Users WHERE ID=?;";
		
		try {	
			
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setInt(1, id);
			
			statement.executeUpdate();
			System.out.println("User removed!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}

//------------------------TESTING-----------------------------|
	//ONLY FOR TESTING OR ONE TIME USE AFTER DROP, NEVER CALL OUTSIDE OF THIS CLASS
	private static void createTableUsers() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users("
								+ "user_id INTEGER PRIMARY KEY,"
								+ "username TEXT NOT NULL,"
								+ "password TEXT NOT NULL," //pridat hash
								+ "role_id INTEGER NOT NULL,"
								+ "email TEXT NOT NULL,"
								+ "FOREIGN KEY(role_id) REFERENCES Roles(role_id)"
								+ ");");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public static void createTableBooks() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Books("
								+ "book_id INTEGER PRIMARY KEY,"
								+ "title TEXT NOT NULL,"
								+ "author TEXT NOT NULL,"
								+ "isbn INTEGER NOT NULL,"
								+ "available_copies INTEGER NOT NULL"
								+ ");");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void createTableRoles() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Roles("
								+ "role_id INTEGER PRIMARY KEY,"
								+ "role_name TEXT NOT NULL);");
			statement.execute("INSERT INTO Roles(role_name) VALUES(\"Admin\")");
			statement.execute("INSERT INTO Roles(role_name) VALUES(\"User\")");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void createTableTransaction() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Transaction("
								+ "transaction_id INTEGER PRIMARY KEY,"
								+ "user_id INTEGER NOT NULL,"
								+ "book_id INTEGER NOT NULL,"
								+ "action TEXT NOT NULL,"
								+ "date DATETIME NOT NULL,"
								+ "FOREIGN KEY(user_id) REFERENCES Users(user_id),"
								+ "FOREIGN KEY(book_id) REFERENCES Books(book_id)"
								+ ");");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	//ONLY FOR TESTING
	private static void dropTable(String tableName) {
		
		String insertSQL = "DROP TABLE ?;";
		
		try {	
			
            PreparedStatement statement = connection.prepareStatement(insertSQL);
            statement.setString(1, tableName);
			
			statement.executeUpdate();
			System.out.println("User added!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}

}
