import Models.Classes.Student;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("#################### DATABAZE STUDENTU ####################");
        System.out.println("#  (Pokracujte vyberem moznosti zadanim cisla do vstupu)  #");
        System.out.println("# ------------------------------------------------------- #");
        System.out.println("# 1) Prijmout studenta na univerzitu (pridat studenta)    #");
        System.out.println("#################### DATABAZE STUDENTU ####################");

        ArrayList<Student> students = new ArrayList<>();

        System.out.print("[#] Zadejte moznost: ");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.next();
        int chosenOption;
        try
        {
            chosenOption = Integer.parseInt(userInput);
        }
        catch (NumberFormatException e)
        {
            System.out.println("[!] CHYBA! Vybrana moznost musi byt cislo!");
            main(null);
            return;
        }

        switch (chosenOption)
        {
            case 1:
                String name;
                String surname;
                String dateOfBirth;

                System.out.print("[1/3] Zadej krestni jmeno studenta: ");
                name = scanner.next();
                System.out.print("[2/3] Zadej prijmeni studenta: ");
                surname = scanner.next();
                System.out.print("[3/3] Zadej datum narozeni studenta (DD.MM.YYYY): ");
                dateOfBirth = scanner.next();

                students.add(new Student(students.size() + 1, name, surname, dateOfBirth));
                break;
        }
    }
}