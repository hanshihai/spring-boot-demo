package com.hans.boot.demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Util {

    private static Util util = new Util();

    public static String load(String filename) throws Exception {
        String result = "";
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(util.getClass().getClassLoader().getResource(filename).openStream()));
            StringBuilder buffer = new StringBuilder();
            String line = null;
            while((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            result = buffer.toString();
            reader.close();
        }catch(Exception e) {
            throw e;
        }
        return result;
    }
}
