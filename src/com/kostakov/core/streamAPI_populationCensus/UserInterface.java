package com.kostakov.core.streamAPI_populationCensus;

import java.time.*;
import java.util.List;


public class UserInterface {

    public static void printMenu() {
        System.out.println("\nВыберите и введите номер из пункта меню:" +
                "\n   1. Добавить информацию о человеке в базу переписи" +
                "\n   2. Удалить информацию о человеке из базы переписи" +
                "\n   3. Вывести список трудоспособного населения с высшим образованием" +
                "\n   4. Вывести список фамилий призывников" +
                "\n   5. Вывести количество несовершеннолетних" +
                "\n   0. Завершить работу программы");
    }

    private static LocalDate createDateOfBirth() {
        var trueData = LocalDate.MIN;
        System.out.print("Введите дату рождения (дд.мм.гггг):" +
                "\n>");
        while (trueData == LocalDate.MIN) {
            var data = Main.consoleReadString().trim();

            try {
                trueData = LocalDate.of(
                        Integer.parseInt(data.substring(6, 10)),
                        Integer.parseInt(data.substring(3, 5)),
                        Integer.parseInt(data.substring(0, 2))
                );
            } catch (RuntimeException exception) {
                System.out.println("Вы ввели некорректную дату рождения, повторите ввод.");
            }

            if (trueData != LocalDate.MIN && trueData.isBefore(LocalDate.now().minusYears(130))
                    || trueData.isAfter(LocalDate.now())) {
                System.out.println("Вы ввели некорректную дату рождения, повторите ввод.");
                trueData = LocalDate.MIN;
            }
        }
        return trueData;
    }

    private static Sex createSex() {
        var sex = Sex.MALE;
        System.out.println("Укажите пол (1/2 - выберите из списка):");
        Sex.printSex();
        boolean invalidSex = false;

        do {
            switch (Main.consoleReadInt()) {
                case 1:
                    sex = Sex.MALE;
                    invalidSex = false;
                    break;
                case 2:
                    sex = Sex.FEMALE;
                    invalidSex = false;
                    break;
                default:
                    System.out.println("Вы ввели некорректные данные, повторите ввод.");
                    invalidSex = true;
                    break;
            }
        } while (invalidSex);

        return sex;
    }

    static void displayAbleBodiedPeople(PopulationCensus populationCensus) {
        System.out.println("\n\t\tКоличество трудоспособного населения с высшим образованием: "
                + populationCensus.ableBodiedPeopleCount());
        System.out.println("\t\tВывести список на экран (да/нет)?");
        String input = Main.consoleReadString().toLowerCase();
        switch (input) {
            case "да":
                List<Person> ableBodiedPeople = populationCensus.ableBodiedPeople();
                System.out.println("\n\t\tСписок трудоспособного населения с высшим образованием:");
                ableBodiedPeople.stream()
                        .forEach(System.out::println);
                break;
            case "нет":
                break;
            default:
                System.out.println("Вы ввели некорректные данные.");
        }
    }

    private static Education createEducation() {
        var education = Education.ELEMENTARY;
        System.out.println("Укажите образование (выберите номер из списка):");
        Education.printEducation();
        boolean invalidEducation = false;

        do {
            switch (Main.consoleReadInt()) {
                case 1:
                    education = Education.NO_EDUCATION;
                    break;
                case 2:
                    education = Education.ELEMENTARY;
                    break;
                case 3:
                    education = Education.SECONDARY;
                    break;
                case 4:
                    education = Education.FURTHER;
                    break;
                case 5:
                    education = Education.HIGHER;
                    break;
                default:
                    System.out.println("Вы ввели некорректные данные, повторите ввод.");
                    invalidEducation = true;
                    break;
            }
        } while (invalidEducation);

        return education;
    }

    private static String createName() {
        System.out.print("Введите имя:" +
                "\n>");
        var name = Main.consoleReadString();
        return name;
    }

    private static String createFamily() {
        System.out.print("Введите фамилию:" +
                "\n>");
        var family = Main.consoleReadString();
        return family;
    }

    public static void createPerson(PopulationCensus populationCensus) {
        Person person = Person.newBuilder()
                .setName(createName())
                .setFamily(createFamily())
                .setSex(createSex())
                .setDateOfBirth(createDateOfBirth())
                .setEducation(createEducation())
                .buildPerson();
        populationCensus.addPerson(person);
    }

    public static void deletePerson(PopulationCensus populationCensus) {
        System.out.println("\n\t\tДля удаления из базы данных введите фамилию и имя через пробел (пример: Иванов Иван).");

        String[] familyName = Main.consoleReadString()
                .replaceAll("\\s{1,}", " ")
                .strip()
                .split(" ");


        if (populationCensus.listByName(familyName)) {
            System.out.println("\n\t\tДля подтверждения удаления из базы данных выберите и введите ID." +
                    "\nДля выхода введите '0'");
            int input = Main.consoleReadInt();
            if (input != 0) {
                Person removePerson = populationCensus.removePerson(input);
                if (removePerson != null) {
                    System.out.println(removePerson.getFamily() + " " + removePerson.getName()
                            + " удалён из базы данных ипереписи населения.");
                } else {
                    System.out.println("\n\t\tЧеловек с ID - " + input + "отсутствует в базе данных переписи населегия.");
                }
            }
        }
    }
}

