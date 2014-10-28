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
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import jlibs.core.util.regex.TemplateMatcher;

/**
 *
 * @author Çağatay
 */
public class ConfigGenerator {
    
    private final String LEFT_SIDE = "__";

    private final String RIGHT_SIDE = "__";

    private final String NAME = "config.php";
    
    private final String BASE_URL;
    
    private final String LANGUAGE;
    
    private final String PATH;

    private TemplateMatcher matcher;

    private FileReader reader;

    private FileWriter writer;

    private Map<String, String> arguments;

    public ConfigGenerator(String baseURL, String language, String path) {
        BASE_URL = baseURL;
        LANGUAGE = language;
        PATH = path;
        matcher = new TemplateMatcher(LEFT_SIDE, RIGHT_SIDE);
        arguments = new HashMap<String, String>();
        arguments.put("NAME", NAME);
        arguments.put("BASEURL", BASE_URL);
        arguments.put("LANGUAGE", LANGUAGE);
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
