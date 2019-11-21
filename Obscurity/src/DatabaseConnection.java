import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JOptionPane;
/**Singleton Object to handle database operations using h2 and an In-Memory database
 * 
 * @author Dominik Steffan
 * 
 *
 */
public class DatabaseConnection {
	
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:mem:credentials;DB_CLOSE_DELAY=-1";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	private Connection connection;
	
	private static DatabaseConnection Singleton = new DatabaseConnection();
	/** Singleton Database Connection Object
	 * 
	 * SQL Statements to create Database:
	 * CREATE TABLE user ( username VARCHAR(50) NOT NULL, password VARCHAR(64) NOT NULL);
	 * insert into USER values ('woeckl','2a859255d5713bed4a05f0d5e91824f751684388fcc765f13f52c492838f400c');
	 * insert into user values ('weakadmin','b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342');
	 * insert into user values ('user','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5');
	 * insert into user values ('admin','e36efef6b45862c70d02a258f8215b93a240f2965fa6d919be5e286bc553ce82');
	 * 
	 */
	private DatabaseConnection () {
		
		connection = getDBConnection();
		Statement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.createStatement();
			statement.execute("CREATE TABLE user (username VARCHAR(9), password VARCHAR(64))");
			statement.execute("insert into USER values('woeckl','2a859255d5713bed4a05f0d5e91824f751684388fcc765f13f52c492838f400c')");
			statement.execute("insert into user values ('weakadmin','b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342')");
			statement.execute("insert into user values ('user','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5')");
			statement.execute("insert into user values ('admin','e36efef6b45862c70d02a258f8215b93a240f2965fa6d919be5e286bc553ce82')");
			
			/*ResultSet rs = statement.executeQuery("select * from user");
            System.out.println("H2 In-Memory Database inserted through Statement");
            while (rs.next()) {
                System.out.println("username " + rs.getString("username") + " password " + rs.getString("password"));
            }*/
            statement.close();
            connection.commit();
			
		} catch (SQLException e) {
            System.out.println("Exception Message " + e.getLocalizedMessage());
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } catch (Exception e) {
            e.printStackTrace();
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        }
	}
	
	 private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }
	/**
	 * @return Singleton Instance of and already established database connection
	 */
	public static DatabaseConnection GetInstance() {
		
		return Singleton;
	}
	
	protected void finalize() throws Throwable  {
		try {
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method is intentionally vulnerable to SQL Injections<br>
	 * You can either bypass authorization or inject SQL to e.g. insert a new user or drop tables<br>
	 * <br>
	 * e.g. try <br>
	 * username: ' or true-- <br>
	 * to bypass authorization<br><br>
	 * username: '; insert into user values ('root','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5')--<br>
	 * to create a new user root with password 12345<br><br>
	 * username: ';CALL CSVWRITE('C:\temp\test.csv', 'SELECT * FROM USER');--<br>
	 * to output the table to csv<br>
	 * <br>
	 * CAUTION! Database is volatile and will be destroyed after every run of the program since  DB_CONNECTION = "jdbc:h2:mem..."<br>
	 * 
	 * @param username Username from login form
	 * @param password Password from Password field which will be hashed using SHA-256 and won't be overwritten with 0 after use to circumvent memory analysis for passwords
	 * @return rather the authentication was successful or not
	 */
	
	public boolean checkLoginUnsafe(String username, char[] password) {

		try {
			String passwordhash = HashFactory.hashString(password, "SHA-256");
			Statement statement = null;
			statement = connection.createStatement();

			ResultSet rs = statement.executeQuery("select * from user where username='"+username+"' and password='"+passwordhash+"' limit 0,1");
			
			if (rs.last())
				if (rs.getRow()>0)
					return true;
			statement.close();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "No match with User "+username+" and the given password.", "User or Password not correct", JOptionPane.WARNING_MESSAGE);
		return false;
	}
	/**
	 * This method checks the user credentials and should not be vulnerable to SQL Injections.
	 * 
	 * @param username Username from login form
	 * @param password Password from Password field which will be hashed using SHA-256 and will be overwritten with 0 after use to circumvent memory analysis for passwords
	 * @return rather the authentication was successful or not
	 */
	public boolean checkLoginSafe(String username, char[] password) {

		try {
			String passwordhash = HashFactory.hashString(password, "SHA-256");
			Arrays.fill(password, '\u0000');
			Statement statement = null;
			statement = connection.createStatement();
			

			ResultSet rs = statement.executeQuery("select username from user where password='"+passwordhash+"'");
			
			while (rs.next()) {
			    // System.out.println("username " + rs.getString("username"));
			    if (rs.getString("username").contentEquals(username))
			    	return true;
			}
			statement.close();
			connection.commit();
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(null, "No match with User "+username+" and the given password.", "User or Password not correct", JOptionPane.WARNING_MESSAGE);
		return false;
	}
	
}
