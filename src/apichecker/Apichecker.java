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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * @author Harrie Bos <polichism@gmail.com>
 */
@SuppressWarnings("serial")
public class Apichecker extends JFrame implements ActionListener {
    
    // Main panel
    JPanel apiPanel;
    
    // Doing the actual API call
    JButton callButton;
    
    // Field to fill in the API URL
    JTextField urlField;
    
    // Result field
    JTextArea resultField;
    
    // Program label
    JLabel myLabel;
    
    // HTTP options select field
    JComboBox httpOptionField;
    
    //Possible HTTP Request Options
    String httpOptions[] = {"GET", "POST", "OPTIONS", "TRACE", "PUT", "DELETE"};
    
    public static void main(String[] args) {
        Apichecker ac = new Apichecker();
        ac.setTitle("API Checker");
        ac.setSize(1024, 786);
        ac.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ac.setVisible(true);
    }
    
    /**
     * Initializing app and building layout
     * 
     * @author: Harrie Bos <polichism@gmail.com>
     */
    public Apichecker() {
        apiPanel = new JPanel();
        apiPanel.setLayout(new BoxLayout(apiPanel, BoxLayout.Y_AXIS));
        
        apiPanel.add(this.urlPanel());
        apiPanel.add(this.headerPanel());
        apiPanel.add(this.paramPanel());
        apiPanel.add(this.resultPanel());
        
        this.add(apiPanel);  
    }
    
    /**
     * The place where the result will be displayed
     * @return JPanel resultPanel
     */
    private JPanel resultPanel() {
        JPanel resultPanel = new JPanel();
        
        resultField = new JTextArea();
        resultField.setSize(200, 200);
        resultField.setToolTipText("Result");
        resultPanel.add(resultField);
        
        return resultPanel;
    }
    
    /**
     * The place where params can be added.
     * Wether this is a get, post or other http option
     * 
     * @return JPanel paramPanel
     */
    private JPanel paramPanel() {
        JPanel paramPanel = new JPanel(); 
        
        return paramPanel;
    }
    
    /**
     * The place where headers can be added.
     * 
     * @return JPanel headerPanel
     */    
    private JPanel headerPanel() {
        JPanel headerPanel = new JPanel();
        
        return headerPanel;
    }
    
    /**
     * The place where URL will be set to call.
     * 
     * @return JPanel urlPanel
     */    
    private JPanel urlPanel() {
        JPanel urlPanel = new JPanel();
        
        urlField = new JTextField(50);
        urlField.setToolTipText("API URL");
        urlField.setText("Api URL");
        
        httpOptionField = new JComboBox(this.httpOptions);
        
        callButton = new JButton("Call");
        callButton.addActionListener(this);
        
        urlPanel.add(httpOptionField);
        urlPanel.add(urlField);
        urlPanel.add(callButton);
        return urlPanel;
    }
    
    /**
     * Doing the actual call to the url filled in urlPanel
     * TODO: Put it in other class
     * TODO: Fix to make other calls instead of only GET too.
     * 
     * @param ActionEvent event 
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == callButton) {
            try {
                System.out.println(this.httpOptions.toString());
                HttpURLConnection request = null;
                URL url;
                url = new URL(this.urlField.getText());

                request = (HttpURLConnection) url.openConnection();
                request.setRequestMethod(this.httpOptionField.getSelectedItem()
                       .toString());
                request.setRequestProperty("User-Agent", "Harrie Test");
                
                int responseCode = request.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
                StringBuffer response;
                try (BufferedReader in = new BufferedReader(
                             new InputStreamReader(request.getInputStream()))) {
                    String inputLine;
                    response = new StringBuffer();
                    while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                    }
                }
                resultField.setText(response.toString());
            } catch (IOException ex) {
                Logger.getLogger(Apichecker.class.getName())
                      .log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
