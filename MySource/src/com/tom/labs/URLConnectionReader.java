package com.tom.labs;

import java.net.*;
import java.io.*;

public class URLConnectionReader {
    public static void main(String[] args) throws Exception {
    	try {
    	    // Construct data
    	    String data = URLEncoder.encode("q", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
    	    data += "&" + URLEncoder.encode("rsv_bp", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");

    	    // Send data
    	    URL url = new URL("http://www.google.com");
    	    URLConnection conn = url.openConnection();
    	    conn.setDoOutput(true);
    	    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    	    wr.write(data);
    	    wr.flush();

    	    // Get the response
    	    BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	    String line;
    	    while ((line = rd.readLine()) != null) {
    	        // Process line...
    	    }
    	    wr.close();
    	    rd.close();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}