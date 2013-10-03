/*
 * Apichecker is a simple program to test the output of API's by filling in
 * a URL, parameters and/or headers.
 * Copyright (C) 2013 Harrie Bos <polichism@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
    
    /**
     * Doing the actual API call
     * @param String requestMethod
     * @param String apiUrl
     * @return String
     */
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
