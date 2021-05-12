/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isstracker;

import java.util.Map;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.io.Reader;
import java.io.BufferedReader;

import java.net.URL;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Iterator;
import org.json.JSONArray;

/**
 *
 * @author bryson
 */
public class IssTracker {

    /**
     * Driver for the application Request information from the website
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //url reading from (returns the iss position)
            JSONObject json = readFromUrl("http://api.open-notify.org/iss-now.json");

            Timestamp ts = new Timestamp(json.getInt("timestamp"));

            System.out.println(json.get("iss_position"));

            //Ignore this this is iteration of JSON objects
            
            /*JSONObject test = json.getJSONObject("iss_position");
            Iterator<String> keys = test.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                if (test.get(key) instanceof String) {
                    // do something with jsonObject here 
                    System.out.println(test.get(key));
                    
                }
            }*/
        } catch (IOException ex) {
            Logger.getLogger(IssTracker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(IssTracker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    /**
     *
     * @param url
     * @return an object of the json given by the website
     * @throws IOException
     * @throws JSONException
     */
    public static JSONObject readFromUrl(String url) throws IOException, JSONException {
        InputStream web = new URL(url).openStream();
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(web, Charset.forName("UTF-8")));
            String jsonText = read(br);
            JSONObject json = new JSONObject(jsonText);

            return json;

        } finally {
            web.close();
        }
    }
    //build the response
    private static String read(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = rd.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }
}
