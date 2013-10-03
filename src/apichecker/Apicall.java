/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package apichecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author polichism
 */
public class Apicall {
   
    public static void main(String[] args) { }
    
    public String call(String requestMethod, String apiUrl) {
        HttpURLConnection request = null;
        URL url;
        
        try {
                url = new URL(apiUrl); 

                request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod(requestMethod.toString());
                request.setRequestProperty("User-Agent", "Harrie Test");
                
                int responseCode = request.getResponseCode();
                StringBuffer response;
                try (BufferedReader in = new BufferedReader(
                             new InputStreamReader(request.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                }
                return response.toString();
            } catch (IOException ex) {
                Logger.getLogger(Apichecker.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
        
       return "";
    }
}
