/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.database;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;

/**
 *
 * @author Quicksort
 */
public abstract class Repository<T> implements IRepository<T> {
    
    protected static final Hashtable<String, String> queriesRegistry = new Hashtable<String, String>();

    private static final String getQuery(String path, boolean useCache, Class type) throws UnsupportedEncodingException, IOException {
	if (useCache && queriesRegistry.containsKey(path)) {
	    return queriesRegistry.get(path);
	}
	String string = "";
	InputStream stream = type.getResourceAsStream(path);
	InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
	char[] buffer = new char[1024];
	while (reader.read(buffer, 0, 1024) != -1) {
	    string += (new String(buffer));
	}
	reader.close();
	stream.close();
	if (useCache) {
	    queriesRegistry.put(String.format("%s/%s", type.toString(), path), string);
	}
	return string;
    }
    
    protected final String getQuery(String path, boolean useCache) throws UnsupportedEncodingException, IOException {
	return Repository.getQuery(path, useCache, this.getClass());
    }
}
