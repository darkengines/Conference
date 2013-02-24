/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.session;

import darkengines.core.database.Database;
import darkengines.core.database.Repository;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public class SessionRepository extends Repository<Session> {

    @Override
    public void install() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	String query = getQuery("create_session_table.sql", true);
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
    public void uninstall() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	String query = getQuery("drop_session_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public void clear() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException {
	String query = getQuery("truncate_session_table.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.execute();
	ps.close();
	connection.close();
    }

    @Override
    public Session map(ResultSet resultSet) throws SQLException {
	Session session = new Session();
	session.setId(resultSet.getLong("id"));
	session.setUserId(resultSet.getLong("user_id"));
	session.setUuid(UUID.fromString(resultSet.getString("uuid")));
	return session;
    }
    
    public Session insertSession(Session session) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("insert_session.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	ps.setObject(1, session.getUserId());
	ps.setObject(2, session.getUuid().toString());
	ps.executeUpdate();
	ResultSet generatedKeys = ps.getGeneratedKeys();
	if (generatedKeys.next()) {
	    session.setId(generatedKeys.getLong(1));
	}
	generatedKeys.close();
	ps.close();
	connection.close();
	return session;
    }
    
    public void deleteSessionByUuid(UUID uuid) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	String query = getQuery("delete_session_by_uuid.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
	ps.setObject(1, uuid.toString());
	ps.executeUpdate();
	ps.close();
	connection.close();
    }
    
    public Session getSessionByUuid(UUID uuid) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NamingException, SQLException {
	Session session = null;
	String query = getQuery("get_session_by_uuid.sql", true);
	Connection connection = Database.getConnection();
	PreparedStatement ps = connection.prepareStatement(query);
	ps.setObject(1, uuid.toString());
	ResultSet resultSet = ps.executeQuery();
	if (resultSet.next()) {
	    session = map(resultSet);
	}
	resultSet.close();
	ps.close();
	connection.close();
	return session;
    }
}
