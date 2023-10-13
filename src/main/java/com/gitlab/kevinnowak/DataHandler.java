package com.gitlab.kevinnowak;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

class DataHandler {
    private URL apiUrl;
    private HttpURLConnection connection;

    DataHandler() {
        try {
            apiUrl = new URL(getApiUrl());
            connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private String getApiUrl() {
        // TODO: Move string to .env file or something
        return "http://api.football-data.org/v4/competitions";
    }


    public void callApiForStandings(League selectedLeague) {

    }
}
