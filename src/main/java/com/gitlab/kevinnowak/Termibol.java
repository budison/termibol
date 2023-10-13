package com.gitlab.kevinnowak;

import java.net.MalformedURLException;

class Termibol {
    private final UserInputHandler userInputHandler;
    private League selectedLeague;
    private final DataHandler dataHandler;

    public Termibol() {
        this.userInputHandler = new UserInputHandler();
        this.selectedLeague = League.NONE;
        this.dataHandler = new DataHandler();
    }

    void start() {
        printBannerAndPrompt();
        readUserInputAndSetSelectedLeague();
        // make API call and receive data
        dataHandler.callApiForStandings(selectedLeague);
        // pass data to message handler and receive formatted string
        // print standings
    }

    private void readUserInputAndSetSelectedLeague() {
        int userInput = userInputHandler.readInt();
        selectedLeague = determineSelectedLeagueFromInt(userInput);
    }

    private League determineSelectedLeagueFromInt(int userInput) {
        return switch (userInput) {
            case 1 -> League.PREMIER_LEAGUE;
            case 2 -> League.BUNDESLIGA;
            case 3 -> League.LA_LIGA;
            case 4 -> League.LIGUE_1;
            default -> League.NONE;
        };
    }

    private void printBannerAndPrompt() {
        System.out.println(MessageHandler.BANNER);
        System.out.print(MessageHandler.LEAGUE_PROMPT);
    }
}
