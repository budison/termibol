package com.gitlab.kevinnowak;

public enum League {
    NONE("None"),
    PREMIER_LEAGUE("Premier League"),
    BUNDESLIGA("Bundesliga"),
    LA_LIGA("La Liga"),
    LIGUE_1("Ligue 1");

    private final String name;

    League(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
