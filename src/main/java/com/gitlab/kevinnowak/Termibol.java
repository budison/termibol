package com.gitlab.kevinnowak;

class Termibol {
    private final UserInputHandler userInputHandler;
    private League selectedLeague;
    private final DataHandler dataHandler;
    private int userInput;

    public Termibol() {
        this.userInputHandler = new UserInputHandler();
        this.selectedLeague = League.NONE;
        this.dataHandler = new DataHandler();
    }

    int start() {
        printBanner();

        do {
            try {
                readUserInput();
            } catch (NoLeagueException e) {
                System.err.println(e.getMessage());
            }
        } while (this.selectedLeague == League.NONE);

        // make API call and receive data
        dataHandler.callApiForStandings(selectedLeague);
        // pass data to message handler and receive formatted string
        // print standings

        return 1;
    }

    void readUserInput() throws NoLeagueException {
        printUserInputPrompt();
        this.userInput = readUserInputAndSetSelectedLeague();

        if (this.selectedLeague == League.NONE) {
            throw new NoLeagueException(String.format(MessageHandler.INVALID_INPUT_MESSAGE, this.userInput));
        }
    }

    int readUserInputAndSetSelectedLeague() {
        this.userInput = userInputHandler.readInt();
        this.selectedLeague = determineSelectedLeagueFromInt(this.userInput);

        return this.userInput;
    }

    League determineSelectedLeagueFromInt(int userInput) {
        return switch (userInput) {
            case 1 -> League.PREMIER_LEAGUE;
            case 2 -> League.BUNDESLIGA;
            case 3 -> League.LA_LIGA;
            case 4 -> League.LIGUE_1;
            default -> League.NONE;
        };
    }

    private void printBanner() {
        System.out.println(MessageHandler.BANNER);
    }

    private void printUserInputPrompt() {
        System.out.print(MessageHandler.USER_INPUT_PROMPT);
    }

    League getSelectedLeague() {
        return selectedLeague;
    }
}
