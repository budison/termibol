package com.gitlab.kevinnowak;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

class DataHandler {

    String callApiForStanding(League selectedLeague) {
        String responseBody = getResponseBody(selectedLeague);
        TableDTO tableDTO = mapToDTO(responseBody);
        return MessageHandler.formatTableDTO(tableDTO);
    }

    private TableDTO mapToDTO(String responseBody) {
        ObjectMapper objectMapper = new ObjectMapper();
        TableDTO tableDTO = new TableDTO(new ArrayList<>());

        try {
            JsonNode rootNode = objectMapper.readTree(responseBody);
            JsonNode tableNode = rootNode.path("standings").get(0).path("table");

            for (JsonNode tableRowNode : tableNode) {
                TableRowDTO tableRowDTO = new TableRowDTO(
                        tableRowNode.path("position").asInt(),
                        tableRowNode.path("team").path("shortName").asText(),
                        tableRowNode.path("playedGames").asInt(),
                        tableRowNode.path("won").asInt(),
                        tableRowNode.path("draw").asInt(),
                        tableRowNode.path("lost").asInt(),
                        tableRowNode.path("goalsFor").asInt(),
                        tableRowNode.path("goalsAgainst").asInt(),
                        tableRowNode.path("goalDifference").asInt(),
                        tableRowNode.path("points").asInt()
                );

                tableDTO.tableRows().add(tableRowDTO);
            }

//            Throw own exception
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return tableDTO;
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
