package com.kostakov.core.streamAPI_populationCensus;

public enum Education {
    NO_EDUCATION("Образование отсутствует"),
    ELEMENTARY("Начальное образование"),
    SECONDARY("Среднее образование"),
    FURTHER("Средне-специальное образование"),
    HIGHER("Высшее образование");

    String rusEducation;

    Education(String rusEducation) {
        this.rusEducation = rusEducation;
    }

    public String getRusEducation() {
        return rusEducation;
    }

    public void setRusEducation(String rusEducation) {
        this.rusEducation = rusEducation;
    }

    public static void printEducation() {
        Education[] education = values();
        var i = 1;
        for (Education e : education) {
            System.out.println(i + ". " + e.rusEducation);
            i++;
        }
    }
}
