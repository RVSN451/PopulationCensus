package com.kostakov.core.streamAPI_populationCensus;

import java.io.*;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Main {
    static final int PEOPLE_AMOUNT = 10;
    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static int consoleReadInt() {
        int voice = 0;
        String input = consoleReadString();

        if (isDigit(input)) {
            voice = Integer.parseInt(input);
        } else {
            System.out.println("Введённое значение не является числом, повторите ввод.");
            voice = consoleReadInt();
        }
        return voice;
    }

    static boolean isDigit(String s) throws NumberFormatException {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String consoleReadString() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static LocalDate randomDateOfBirth() {
        long minDay = LocalDate.of(1940, 1, 1).toEpochDay();
        long maxDay = LocalDate.now().toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        LocalDate randomDate = LocalDate.ofEpochDay(randomDay);
        return randomDate;
    }

    public static void generationPeople(PopulationCensus populationCensus, int amount) {
        List<String> maleName = Arrays.asList("Иван", "Филипп", "Сергей", "Василий", "Пётр",
                "Ярослав", "Игнат", "Григорий", "Константин", "Аркадий", "Яков", "Дмитрий", "Виктор", "Василий");
        List<String> maleFamily = Arrays.asList("Иванов", "Филиппов", "Сергеев", "Тёркин", "Цой",
                "Дубов", "Игнатьев", "Панкратов", "Михайлов", "Абрамов", "Ежов", "Рыжов", "Глазов");
        List<String> femaleName = Arrays.asList("Татьяна", "Елена", "Анастасия", "Дарья", "Марфа",
                "Клавдия", "Шура", "София", "Тамара", "Валерия", "Сирафима", "Галина", "Ольга");
        List<String> femaleFamily = Arrays.asList("Иванова", "Филиппова", "Сергеева", "Васильева", "Цой",
                "Путина", "Изосимова", "Панкратова", "Шаронова", "Пугачева", "Носова", "Трелина", "Зайцева");

        for (int i = 0; i < PEOPLE_AMOUNT; i++) {
            LocalDate dateOfBirth = randomDateOfBirth();
            int age = (int) ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
            Education education = Education.NO_EDUCATION;
            if (age > 17 && age < 20) {
                education = Education.values()[1 + (new Random().nextInt(Education.values().length - 1))];
            } else if (age >= 20) {
                education = Education.values()[2 + (new Random().nextInt(Education.values().length - 2))];
            }

            if (Sex.values()[new Random().nextInt(Sex.values().length)] == Sex.FEMALE) {
                populationCensus.addPerson(new Person(
                        femaleName.get(new Random().nextInt(femaleName.size())),
                        femaleFamily.get(new Random().nextInt(femaleFamily.size())),
                        dateOfBirth,
                        Sex.FEMALE,
                        education
                ));
            } else {
                populationCensus.addPerson(new Person(
                        maleName.get(new Random().nextInt(maleName.size())),
                        maleFamily.get(new Random().nextInt(maleFamily.size())),
                        dateOfBirth,
                        Sex.MALE,
                        education
                ));
            }
        }
    }

    public static void main(String[] args) {
        PopulationCensus populationCensus = new PopulationCensus();

        generationPeople(populationCensus, PEOPLE_AMOUNT);

        int i = -1;

        UserInterface.printMenu();

        while (true) {
            switch (consoleReadInt()) {
                case 0:
                    i = 0;
                    break;
                case 1:
                    UserInterface.createPerson(populationCensus);
                    UserInterface.printMenu();
                    break;

                case 2:
                    UserInterface.deletePerson(populationCensus);
                    UserInterface.printMenu();
                    break;

                case 3:
                    UserInterface.displayAbleBodiedPeople(populationCensus);
                    UserInterface.printMenu();
                    break;

                case 4:
                    System.out.println("Фамилии призывников: " + populationCensus.conscriptFamilyList());
                    UserInterface.printMenu();
                    break;

                case 5:
                    System.out.println("Количество жителей не достигших 18 лет:  "
                            + populationCensus.ageGroup(0, 17) + " человек.");
                    UserInterface.printMenu();
                    break;

                default:
                    System.out.println("Вы ввели неверное значение, повторите выбор:");
                    break;
            }
            if (i == 0) break;
        }
    }
}
