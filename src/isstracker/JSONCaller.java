package isstracker;

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


/**
 *
 * @author bryson
 */
public class JSONCaller {

    private JSONObject info;
    private JSONObject location;

    /**
     * when a new tracker is born I just grab all the data anyway
     */
    public JSONCaller() {
        update();
    }

    /**
     * I just needed somewhere to store it and easily grab it 
     * @return returned the last generated longitude cord
     */
    public double getLongi() {
        double longi = location.getDouble("longitude");
        return longi;
    }

    /**
     * I just needed somewhere to store it and easily grab it 
     * @return returned the last generated latitude cord
     */
    public double getLati() {
        double lati = location.getDouble("latitude");
        return lati;
    }

    /**
     *  Retrieves latest info about the iss and builds its location into an object
     */
    public void update() {
        try {
            //url reading from (returns the iss position)
            info = readFromUrl("http://api.open-notify.org/iss-now.json");
            //create a location for easy access of keys
            //format of this object is {"longitude": xx,"latitude":xx}
            location = info.getJSONObject("iss_position");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(JSONCaller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception e) {
            System.out.println("Something went wrong " + e.getMessage());
        }
    }

    /**
     * Open up communication with the web and read the response it will send back
     * @param url
     * @return an object of the json given by the website
     * @throws IOException
     * @throws JSONException
     */
    public JSONObject readFromUrl(String url) throws IOException, JSONException {
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
    private String read(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int c;
        while ((c = rd.read()) != -1) {
            sb.append((char) c);
        }
        return sb.toString();
    }

}
//Ignore this this is iteration of JSON objects that I used for testing
/*JSONObject test = json.getJSONObject("iss_position");
            Iterator<String> keys = test.keys();

            while (keys.hasNext()) {
            String key = keys.next();
            if (test.get(key) instanceof String) {
            // do something with jsonObject here
            System.out.println(test.get(key));
            
            }
            }*/
