import Models.Classes.CybersecurityStudent;
import Models.Classes.Student;
import Models.Classes.TelecommunicationsStudent;

import java.util.*;

public class Main {
    private static HashMap<Integer, Student> students = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("#################### DATABAZE STUDENTU ####################");
        System.out.println("#  (Pokracujte vyberem moznosti zadanim cisla do vstupu)  #");
        System.out.println("# ------------------------------------------------------- #");
        System.out.println("# 1) Prijmout studenta na univerzitu (pridat studenta)    #");
        System.out.println("# 2) Zadat studentovi novou znamku                        #");
        System.out.println("#################### DATABAZE STUDENTU ####################");

        System.out.print("[>] Zadejte moznost: ");
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

            ContinueByPressingAnyKey();
            return;
        }

        switch (chosenOption)
        {
            case 1:
                System.out.println("");
                System.out.println("--->   Moznost 1: Pridani noveho studenta na univerzitu   <---");

                String studentGroup;
                String name;
                String surname;
                String dateOfBirth;

                System.out.print("[1/4] Je to student (K)yberbezpecnosti nebo (T)elekomunikaci? ");
                studentGroup = scanner.next();
                if (!Objects.equals(studentGroup, "K") && !Objects.equals(studentGroup, "T"))
                {
                    System.out.println("[!] CHYBA! Obor studia musi byt K nebo T!");

                    System.out.println("[#] Pokracujte stisknutim jakekoliv klavesy...");
                    scanner.nextLine();
                    scanner.nextLine();
                    main(null);
                    return;
                }
                System.out.print("[2/4] Zadejte krestni jmeno studenta: ");
                name = scanner.next();
                System.out.print("[3/4] Zadejte prijmeni studenta: ");
                surname = scanner.next();
                System.out.print("[4/4] Zadejte datum narozeni studenta (DD.MM.YYYY): ");
                dateOfBirth = scanner.next();

                if (studentGroup.equals("K"))
                    students.put(students.size() + 1, new CybersecurityStudent(students.size() + 1, name, surname, dateOfBirth));
                else
                    students.put(students.size() + 1, new TelecommunicationsStudent(students.size() + 1, name, surname, dateOfBirth));
                Student lastAddedStudent = students.get(students.size());
                System.out.println("[>] USPECH! Byl pridan student: (#" + lastAddedStudent.getId() + ") " + lastAddedStudent.getName() + " " + lastAddedStudent.getSurname());

                ContinueByPressingAnyKey();
                return;

            case 2:
                System.out.println("");
                System.out.println("--->   Moznost 2: Zadani nove znamky studentovi   <---");

                int id;
                int grade;
                System.out.print("[1/2] Zadejte ID studenta: ");
                String idString = scanner.next();
                try
                {
                    id = Integer.parseInt(idString);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("[!] CHYBA! ID studenta musi byt cele cislo!");

                    ContinueByPressingAnyKey();
                    return;
                }
                if (!students.containsKey(id))
                {
                    System.out.println("[!] CHYBA! Student s timto ID nebyl nalezen!");

                    ContinueByPressingAnyKey();
                    return;
                }

                System.out.print("[2/2] Zadejte novou znamku: ");
                String gradeString = scanner.next();
                try
                {
                    grade = Integer.parseInt(gradeString);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("[!] CHYBA! Nova znamka studenta musi byt cele cislo!");

                    ContinueByPressingAnyKey();
                    return;
                }
                if (grade < 1 || grade > 5)
                {
                    System.out.println("[!] CHYBA! Nova znamka musi byt v rozsahu 1-5!");

                    ContinueByPressingAnyKey();
                    return;
                }

                Student targetStudent = students.get(id);
                targetStudent.AddGrade(grade);
                System.out.println("[>] USPECH! Studentovi: (#" + targetStudent.getId() + ") " + targetStudent.getName() + " " + targetStudent.getSurname() + " byla pridana znamka: " + grade);

                ContinueByPressingAnyKey();
                return;
        }
    }

    private static void ContinueByPressingAnyKey()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[#] Pokracujte stisknutim jakekoliv klavesy...");
        scanner.nextLine();
        main(null);
    }
}