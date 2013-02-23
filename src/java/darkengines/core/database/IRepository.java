/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.database;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;

/**
 *
 * @author Quicksort
 */
public interface IRepository<T> {
    void install() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException;
    void reinstall() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException;
    void uninstall() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException;
    void clear() throws UnsupportedEncodingException, IOException, SQLException, ClassNotFoundException, NamingException;
    T map(ResultSet resultSet) throws SQLException;
}
