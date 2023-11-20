package com.gitlab.kevinnowak;

class MessageHandler {
    static final String BANNER = generateBanner();
    static final String USER_INPUT_PROMPT = generateUserInputPrompt();
    static final String INVALID_INPUT_MESSAGE = generateInvalidInputMessage();

    private static String generateInvalidInputMessage() {
        return """
               
               Your input "%d" is invalid! Please try again.
               """;
    }

    private static String generateUserInputPrompt() {
        return """
                1) %s
                2) %s
                3) %s
                4) %s
                
                Which league's standings would you like to see:\s"""
                .formatted(League.PREMIER_LEAGUE, League.BUNDESLIGA, League.LA_LIGA, League.LIGUE_1);
    }

    private static String generateBanner() {
        return """
                  _______                  _ _           _
                 |__   __|                (_) |         | |
                    | | ___ _ __ _ __ ___  _| |__   ___ | |
                    | |/ _ \\ '__| '_ ` _ \\| | '_ \\ / _ \\| |
                    | |  __/ |  | | | | | | | |_) | (_) | |
                    |_|\\___|_|  |_| |_| |_|_|_.__/ \\___/|_|
                """;
    }
}
