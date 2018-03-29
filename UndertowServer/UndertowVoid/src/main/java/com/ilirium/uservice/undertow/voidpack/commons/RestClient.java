package com.ilirium.uservice.undertow.voidpack.commons;

import java.io.*;
import java.net.*;

public class RestClient {

    public void call(String endpoint, String urlParameters) {
        try {

            URL url = new URL(endpoint);
            String query = urlParameters;

            //make connection
            URLConnection urlc = url.openConnection();

            //use post mode
            urlc.setDoOutput(true);
            urlc.setAllowUserInteraction(false);

            //send query
            PrintStream ps = new PrintStream(urlc.getOutputStream());
            //ps.print(query);
            ps.close();

            //get result
            BufferedReader br = new BufferedReader(new InputStreamReader(urlc.getInputStream()));
            String l = null;
            while ((l = br.readLine()) != null) {
                System.out.println(l);
            }
            br.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
