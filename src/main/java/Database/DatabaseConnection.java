package Database;

import java.sql.*;

public class DatabaseConnection {
	public static ResultSet rs;
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

		}catch(SQLException e) {
			return "SQLite connection failed: " + e;
		}
	}

//------------------------TESTING-----------------------------|
	//ONLY FOR TESTING OR ONE TIME USE AFTER DROP, NEVER CALL OUTSIDE OF THIS CLASS
	private static void createTableUsers() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Users("
								+ "user_id INTEGER PRIMARY KEY,"
								+ "username TEXT NOT NULL,"
								+ "password TEXT NOT NULL,"
								+ "role_id INTEGER DEFAULT 2,"
								+ "email TEXT NOT NULL UNIQUE,"
								+ "FOREIGN KEY(role_id) REFERENCES Roles(role_id)" //, CHECK(role_id BETWEEN 1 AND 2)
								+ ");");
			System.out.println("Table Users created!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void createTableBooks() {
		try {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Books("
								+ "book_id INTEGER PRIMARY KEY,"
								+ "title TEXT NOT NULL,"
								+ "author TEXT DEFAULT \"Unknown\","
								+ "isbn INTEGER NOT NULL UNIQUE,"
								+ "available_copies INTEGER NOT NULL"
								+ ");");
			System.out.println("Table Books created!");
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
			System.out.println("Table Roles created!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void createTableTransaction() {
		try {	statement = connection.createStatement();
		
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS Borrows(" //I cant name it Transaction :(
								+ "transaction_id INTEGER PRIMARY KEY,"
								+ "user_id INTEGER NOT NULL,"
								+ "book_id INTEGER NOT NULL,"
								+ "action TEXT NOT NULL,"
								+ "date TEXT NOT NULL,"
								+ "FOREIGN KEY(user_id) REFERENCES Users(user_id),"
								+ "FOREIGN KEY(book_id) REFERENCES Books(book_id)"
								+ ");");
			System.out.println("Table Borrows created!");
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}
	
	private static void dropTable(String tableName) {		
		try {	
			
			statement.executeUpdate("DROP TABLE "+tableName+";");
			System.out.println("Table "+tableName+" dropped!");
		}catch(SQLException e){
			System.err.println(e.getMessage());
		}
	}

}
