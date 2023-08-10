package org.example;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class httpConnection {

    public static List<String> checkLink(String newUrl, String link) throws IOException {

        List<String> BrokenURL = new ArrayList<>();

        URL MainURL = new URL(newUrl);
        URL baseUrl = new URL(newUrl);
        URL concatenatedURL = new URL(baseUrl, link);
        HttpURLConnection conn = (HttpURLConnection) MainURL.openConnection();
        conn.setRequestMethod("HEAD");
        int response = conn.getResponseCode();

        if (response != 200) {

            System.out.println("the system is broken");
            BrokenURL.add(MainURL.toString());


        } else {


            try {

                HttpURLConnection connection = (HttpURLConnection) concatenatedURL.openConnection();
                connection.setRequestMethod("HEAD");
                int responseCode = connection.getResponseCode();

                if (responseCode !=200) {
                    System.out.println("Broken link with response status of " + responseCode);
                    BrokenURL.add(concatenatedURL.toString());


                } else {
                    System.out.println("The system is working with status code of " + responseCode);


                }

            } catch (IOException e) {
                System.out.println("The system has an error: " + e);


            }

        }
        return BrokenURL;
    }
}


