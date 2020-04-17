package dbconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Herbert Seewann
 */
public class DBPropertiesManager {

    private final Map<String, String> propertiesMap = new HashMap();
    public static final int URL = 0;
    public static final int DRIVER = 1;
    public static final int USERNAME = 2;
    public static final int PASSWORD = 3;
    private static final List<String> REQUIRED_KEYS = Arrays.asList("db.url",
            "db.driver",
            "db.username",
            "db.password"
    );

    public static DBPropertiesManager getInstance() {
        return DBPropertiesManagerHolder.INSTANCE;
    }

    private static class DBPropertiesManagerHolder {

        private static final DBPropertiesManager INSTANCE = new DBPropertiesManager();
    }

    public void createProperties(String file) throws Exception {
        load(file);
    }

    public void load(String filename) throws Exception {
        File file = new File(filename);
        BufferedReader buffy = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffy.readLine()) != null) {
            String[] token = line.split("=");
            if (!REQUIRED_KEYS.contains(token[0])) {
                throw new Exception("Wrong Parameter in config file: " + token[0]);
            }
            propertiesMap.put(token[0], token[1]);
        }
    }

    public String getUrl() {
        return propertiesMap.get(REQUIRED_KEYS.get(URL));
    }

    public String getDriver() {
        return propertiesMap.get(REQUIRED_KEYS.get(DRIVER));
    }

    public String getUsername() {
        return propertiesMap.get(REQUIRED_KEYS.get(USERNAME));
    }

    public String getPassword() {
        return propertiesMap.get(REQUIRED_KEYS.get(PASSWORD));
    }

    @Override
    public String toString() {
        return "DBPropertiesManager{" + "propertiesMap=" + propertiesMap + '}';
    }
}