/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.user;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class UserRepository extends Repository<User> {

    @Override
    public void install() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	String query = getQuery("create_user_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void reinstall() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	uninstall();
	install();
    }

    @Override
    public void uninstall() throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("drop_user_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void clear() throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("truncate_user_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public User map(ResultSet resultSet) throws SQLException {
	User user = new User();
	user.setId(resultSet.getLong("id"));
	user.setEmail(resultSet.getString("email"));
	user.setPassword(resultSet.getString("password"));
	user.setDisplayName(resultSet.getString("display_name"));
	return user;
    }
    
    public User getUserByEmail(String email) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	User user = null;
	String query = getQuery("get_user_by_email.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, email);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    user = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return user;
    }
    
     public User getUserById(Long id) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	User user = null;
	String query = getQuery("get_user_by_id.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, id);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    user = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return user;
    }
    
    public User getUserByCredentials(String email, String password) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	User user = null;
	String query = getQuery("get_user_by_credentials.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, email);
	ps.setObject(2, password);
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    user = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return user;
    }
    
    public User insertUser(User user) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("insert_user.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	ps.setObject(1, user.getEmail());
	ps.setObject(2, user.getPassword());
	ps.setObject(3, user.getDisplayName());
	ps.executeUpdate();
	ResultSet generatedKeys = ps.getGeneratedKeys();
	if (generatedKeys.next()) {
	    user.setId(generatedKeys.getLong(1));
	}
	generatedKeys.close();
	ps.close();
	connection.close();
	return user;
    }
}
