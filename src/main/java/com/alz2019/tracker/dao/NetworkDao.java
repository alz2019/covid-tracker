package com.alz2019.tracker.dao;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkDao {
    public String request(String endpoint) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();

        URL url = new URL(endpoint);

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        try {
            InputStream inputStream = urlConnection.getInputStream();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

            InputStreamReader inputStreamReader = new InputStreamReader(bufferedInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String inputLine = bufferedReader.readLine();
            while (inputLine != null) {
                stringBuilder.append(inputLine);
                inputLine = bufferedReader.readLine();
            }
        } finally {
            urlConnection.disconnect();
        }

        return stringBuilder.toString();
    }
}
