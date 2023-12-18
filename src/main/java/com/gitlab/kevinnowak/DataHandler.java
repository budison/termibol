package com.gitlab.kevinnowak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class DataHandler {

    void callApiForStanding(League selectedLeague) {
        String responseBody = getResponseBody(selectedLeague);
        parseResponse(responseBody); // return Standing object
    }

    private void parseResponse(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode standingsNode = rootNode.path("standings").get(0).path("table");

            for (JsonNode teamNode : standingsNode) {
                int position = teamNode.path("position").asInt();
                String teamName = teamNode.path("team").path("name").asText();

                System.out.printf("Position: %d, Club: %s\n",
                        position, teamName);
            }

//            Throw own exception
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String getResponseBody(League selectedLeague) {
        Configuration configuration = new Configuration();
        String token = configuration.getProperty("api.token");
        StringBuilder sb = new StringBuilder(configuration.getProperty("api.url"));

        switch (selectedLeague) {
            case LA_LIGA -> sb.append("/PD");
            case LIGUE_1 -> sb.append("/FL1");
            case BUNDESLIGA -> sb.append("/BL1");
            case PREMIER_LEAGUE -> sb.append("/PL");
            case NONE -> System.exit(1);
        }

        sb.append("/standings");

        try {
            URL url = new URL(sb.toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("X-Auth-Token", token);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                in.close();

                return response.toString();

            }
//            Throw own exception
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
