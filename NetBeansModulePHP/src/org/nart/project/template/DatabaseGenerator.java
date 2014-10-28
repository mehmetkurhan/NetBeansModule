/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.nart.project.template;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import jlibs.core.util.regex.TemplateMatcher;

/**
 *
 * @author Çağatay
 */
public class DatabaseGenerator {
    private final String LEFT_SIDE = "__";

    private final String RIGHT_SIDE = "__";

    private final String NAME = "database.php";
    
    private final String DSN;
    
    private final String HOST_NAME;
    
    private final String USER_NAME;
    
    private final String PASSWORD;
    
    private final String DATABASE;
    
    private final String PCONNECT;
    
    private final String DB_DEBUG;
    
    private final String CACHE_ON;
    
    private final String PATH;

    private TemplateMatcher matcher;

    private FileReader reader;

    private FileWriter writer;

    private Map<String, String> arguments;

    public DatabaseGenerator(String dsn, String hostName, String userName, 
            String password, String database, String pConnect, String dbDebug, 
            String cacheOn, String path) {
        DSN = dsn;
        HOST_NAME = hostName;
        USER_NAME = userName;
        PASSWORD = password;
        DATABASE = database;
        PCONNECT = pConnect;
        DB_DEBUG = dbDebug;
        CACHE_ON = cacheOn;
        PATH = path;
        matcher = new TemplateMatcher(LEFT_SIDE, RIGHT_SIDE);
        arguments = new HashMap<String, String>();
        arguments.put("NAME", NAME);
        arguments.put("DSN", DSN);
        arguments.put("HOSTNAME", HOST_NAME);
        arguments.put("USERNAME", USER_NAME);
        arguments.put("PASSWORD", PASSWORD);
        arguments.put("DATABASE", DATABASE);
        arguments.put("PCONNECT", PCONNECT);
        arguments.put("DBDEBUG", DB_DEBUG);
        arguments.put("CACHEON", CACHE_ON);
    }

    public void replace(String readFile) {
        File file = new File(PATH + "\\Templates");
        file.mkdir();
        String writeFile = PATH + "\\Templates\\" + NAME;
        reader = null;
        writer = null;
        try {
                reader = new FileReader(readFile);
                writer = new FileWriter(writeFile);
                matcher.replace(reader, writer, arguments);
                reader.close();
                writer.close();
            } catch (FileNotFoundException e) {
                    e.printStackTrace();
            } catch(IOException e) {
                    e.printStackTrace();
            }
    }
}
