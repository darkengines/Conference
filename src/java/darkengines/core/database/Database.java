/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.database;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 *
 * @author Quicksort
 */
public class Database {

    private static DataSource dataSource = null;
    private static Database instance = null;

    private Database() throws ClassNotFoundException, NamingException {
	Class.forName("com.mysql.jdbc.Driver");
	dataSource = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/db");
    }

    public static Connection getConnection() throws ClassNotFoundException, NamingException, SQLException {
	if (instance == null) {
	    instance = new Database();
	}
	return Database.dataSource.getConnection();
    }
}
