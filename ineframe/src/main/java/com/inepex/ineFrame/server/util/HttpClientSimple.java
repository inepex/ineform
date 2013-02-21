package com.inepex.ineFrame.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpClientSimple {

	public static String get(String address) throws IOException{
	    try {
	        // Create a URL for the desired page
	        URL url = new URL(address);
	        
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();	
	        conn.setAllowUserInteraction(false); // no user interact [like pop up]
	        conn.setRequestProperty("Content-Type", "text/html;charset=UTF-8");
	    
	        // Read all the text returned by the server
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
	        String str;
	        String result = "";
	        while ((str = in.readLine()) != null) {
	            // str is one line of text; readLine() strips the newline character(s)
	        	result += str + "\n";
	        }
	        in.close();
	        return result;
	    } catch (MalformedURLException e) {
	    }
	    return "";
	}
}