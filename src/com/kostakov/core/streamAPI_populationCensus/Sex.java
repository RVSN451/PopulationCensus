package com.kostakov.core.streamAPI_populationCensus;

import java.util.Arrays;

public enum Sex {
    MALE("Мужской"),
    FEMALE("Женский");

    String rusSex;

    Sex(String rusSex) {
        this.rusSex = rusSex;
    }

    public String getRusSex() {
        return rusSex;
    }

    public void setRusSex(String rusSex) {
        this.rusSex = rusSex;
    }

    public static void printSex() {
        Sex sex[] = values();
        var i = 1;
        for (Sex s : sex) {
            System.out.println(i + ". " + s.rusSex);
            i++;
        }
    }
}
