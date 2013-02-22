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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class UserRepository extends Repository<User> {

    @Override
    public void install() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException{
	String query = getQuery("create_user_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void reinstall() throws UnsupportedEncodingException, IOException {
	getQuery("install_user_table.sql", true);
    }

    @Override
    public void uninstall() throws UnsupportedEncodingException, IOException {
	getQuery("install_user_table.sql", true);
    }

    @Override
    public void clear() throws UnsupportedEncodingException, IOException {
	getQuery("install_user_table.sql", true);
    }

    @Override
    public User map(ResultSet resultSet) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
