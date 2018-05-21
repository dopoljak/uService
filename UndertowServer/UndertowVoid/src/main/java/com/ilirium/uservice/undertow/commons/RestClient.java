package com.ilirium.uservice.undertow.commons;

import java.io.*;
import java.net.*;

public class RestClient {

    public void call(String endpoint, String urlParameters) {
        try {
            URL url = new URL(endpoint);
            String query = urlParameters;

            //make connection
            URLConnection urlConnection = url.openConnection();

            //use post mode
            urlConnection.setDoOutput(true);
            urlConnection.setAllowUserInteraction(false);

            //send query
            PrintStream printStream = new PrintStream(urlConnection.getOutputStream());
            //printStream.print(query);
            printStream.close();

            //get result
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
            bufferedReader.close();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
