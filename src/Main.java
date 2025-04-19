import Models.Classes.CybersecurityStudent;
import Models.Classes.Student;
import Models.Classes.TelecommunicationsStudent;
import Utils.DbConnector;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class Main {
    private static HashMap<Integer, Student> students = new HashMap<>();

    public static void main(String[] args) {
        DbConnector dbConn = new DbConnector();

        if (!Arrays.equals(args, new String[]{"halabala"}))
            students = dbConn.loadFromDb();

        System.out.println("#################### DATABAZE STUDENTU ####################");
        System.out.println("#  (Pokracujte vyberem moznosti zadanim cisla do vstupu)  #");
        System.out.println("# ------------------------------------------------------- #");
        System.out.println("# 1) Prijmout studenta na univerzitu (pridat studenta)    #");
        System.out.println("# 2) Zadat studentovi novou znamku                        #");
        System.out.println("# 3) Propustit studenta z univerzity (smazat studenta)    #");
        System.out.println("# 4) Vypsat informace o studentovi (vc. stud. prumeru)    #");
        System.out.println("# 5) Spustit dovednost studenta                           #");
        System.out.println("# 6) Vypisat abecedne serazene studenty podle oboru       #");
        System.out.println("# 7) Vypsat obecny studijni prumer                        #");
        System.out.println("# 8) Vypsat pocet studentu v oborech                      #");
        System.out.println("# 9) Ulozit studenta do souboru                           #");
        System.out.println("# 10) Nacist studenta ze souboru                          #");
        System.out.println("# 11) Ukoncit program (ulozit do DB)                      #");
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

            continueByPressingAnyKey();
            return;
        }

        switch (chosenOption)
        {
            case 1:
                System.out.println();
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
                    main(new String[]{"halabala"});
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

                continueByPressingAnyKey();
                return;

            case 2:
                System.out.println();
                System.out.println("--->   Moznost 2: Zadani nove znamky studentovi   <---");

                int id;
                int grade;
                id = getStudentIdFromInput(scanner, 1, 2);
                if (id == -1) return;

                System.out.print("[2/2] Zadejte novou znamku: ");
                String gradeString = scanner.next();
                try
                {
                    grade = Integer.parseInt(gradeString);
                }
                catch (NumberFormatException e)
                {
                    System.out.println("[!] CHYBA! Nova znamka studenta musi byt cele cislo!");

                    continueByPressingAnyKey();
                    return;
                }
                if (grade < 1 || grade > 5)
                {
                    System.out.println("[!] CHYBA! Nova znamka musi byt v rozsahu 1-5!");

                    continueByPressingAnyKey();
                    return;
                }

                Student targetStudent = students.get(id);
                targetStudent.addGrade(grade);
                System.out.println("[>] USPECH! Studentovi: (#" + targetStudent.getId() + ") " + targetStudent.getName() + " " + targetStudent.getSurname() + " byla pridana znamka: " + grade);

                continueByPressingAnyKey();
                return;

            case 3:
                System.out.println();
                System.out.println("--->   Moznost 3: Propustit studenta z univerzity   <---");

                id = getStudentIdFromInput(scanner, 1, 1);
                if (id == -1) return;

                students.remove(id);
                System.out.println("[>] USPECH! Student s ID: " + id + " byl smazan.");

                continueByPressingAnyKey();
                return;

            case 4:
                System.out.println();
                System.out.println("--->   Moznost 4: Vypsat informace o studentovi   <---");

                id = getStudentIdFromInput(scanner, 1, 1);
                if (id == -1) return;

                System.out.println("[>] Jmeno: " + students.get(id).getName());
                System.out.println("[>] Prijmeni: " + students.get(id).getSurname());
                System.out.println("[>] Datum narozeni: " + students.get(id).getDateOfBirth());
                System.out.println("[>] Studijni prumer: " + students.get(id).getAvgGrade());

                continueByPressingAnyKey();
                return;

            case 5:
                System.out.println();
                System.out.println("--->   Moznost 5: Spustit dovednost studenta   <---");

                id = getStudentIdFromInput(scanner, 1, 1);
                if (id == -1) return;

                String returnValue;
                if (students.get(id) instanceof CybersecurityStudent)
                    returnValue = ((CybersecurityStudent) students.get(id)).getNameAsHash();
                else
                    returnValue = ((TelecommunicationsStudent) students.get(id)).getNameInMorse();

                System.out.println("[>] USPECH! Vysledek ability studenta: " + returnValue + ".");

                continueByPressingAnyKey();
                return;

            case 6:
                System.out.println();
                System.out.println("--->   Moznost 6: Vypsat abecedne serazene studenty podle oboru   <---");

                SortedMap<String, Student> sortedMapCybersec = new TreeMap<>();
                SortedMap<String, Student> sortedMapTelecomm = new TreeMap<>();
                students.forEach((k,v) -> {
                    if (v instanceof CybersecurityStudent)
                        sortedMapCybersec.put(v.getSurname(), v);
                    else
                        sortedMapTelecomm.put(v.getSurname(), v);
                });

                System.out.println("[>] Studenti oboru kyberbezpecnosti:");
                sortedMapCybersec.forEach((k, v) -> System.out.println("(#" + v.getId() + ") jmeno: " + v.getName() + ", prijmeni: " + v.getSurname() + ", narozen/a: " + v.getDateOfBirth() + ", stud. prumer: " + v.getAvgGrade()));
                System.out.println("[>] Studenti oboru telekomunikaci:");
                sortedMapTelecomm.forEach((k, v) -> System.out.println("(#" + v.getId() + ") jmeno: " + v.getName() + ", prijmeni: " + v.getSurname() + ", narozen/a: " + v.getDateOfBirth() + ", stud. prumer: " + v.getAvgGrade()));

                continueByPressingAnyKey();
                return;

            case 7:
                System.out.println();
                System.out.println("--->   Moznost 7: Vypsat obecny studijni prumer   <---");

                int[] sum = {0, 0};
                final int[] studentsCount = {0, 0};
                students.forEach((k, v) -> {
                    if (v instanceof TelecommunicationsStudent) {
                        sum[0] += v.getAvgGrade();
                        studentsCount[0]++;
                    }
                    else {
                        sum[1] += v.getAvgGrade();
                        studentsCount[1]++;
                    }
                });

                System.out.println("[>] Obecny studijni prumer na Telekomunikacich: " + ((float) sum[0] / studentsCount[0]) + ".");
                System.out.println("[>] Obecny studijni prumer na Kyberbezpecnosti: " + ((float) sum[1] / studentsCount[1]) + ".");

                continueByPressingAnyKey();
                return;

            case 8:
                System.out.println();
                System.out.println("--->   Moznost 8: Vypsat pocet studentu v oborech   <---");

                sum = new int[]{0, 0};
                students.forEach((k, v) -> {
                    if (v instanceof TelecommunicationsStudent)
                        sum[0]++;
                    else
                        sum[1]++;
                });

                System.out.println("[>] Pocet studentu na Telekomunikacich: " + sum[0] + ".");
                System.out.println("[>] Pocet studentu na Kyberbezpecnosti: " + sum[1] + ".");

                continueByPressingAnyKey();
                return;

            case 9:
                System.out.println();
                System.out.println("--->   Moznost 9: Ulozit studenta do souboru   <---");

                id = getStudentIdFromInput(scanner, 1, 1);
                if (id == -1) return;

                File file = new File("./" + id + ".csv");
                try {
                    FileWriter fw = new FileWriter(file);
                    targetStudent = students.get(id);
                    String type = (targetStudent instanceof TelecommunicationsStudent) ? "Telekomunikace" : "Kyberbezpecnost";
                    fw.write("Obor,Jmeno,Prijmeni,Datum narozeni,Znamky\n"
                            + type + "," + targetStudent.getName() + "," + targetStudent.getSurname() + "," + targetStudent.getDateOfBirth() + "," + targetStudent.getGrades());
                    fw.close();
                }
                catch (Exception e)
                {
                    System.out.println("[!] CHYBA! Nepodarilo se otevrit / vytvorit soubor pro export!");

                    continueByPressingAnyKey();
                    return;
                }

                System.out.println("[>] USPECH! Data byla vyexporovana do souboru: " + id + ".csv.");

                continueByPressingAnyKey();
                return;

            case 10:
                System.out.println();
                System.out.println("--->   Moznost 10: Nacist studenta ze souboru   <---");

                System.out.print("[1/1] Zadejte nazev souboru (vc. pripony): ");
                String fileName = scanner.next();

                file = new File("./" + fileName);
                try {
                    String output;
                    FileReader fr = new FileReader(file);
                    BufferedReader br = new BufferedReader(fr);
                    br.readLine();
                    output = br.readLine();
                    br.close();
                    fr.close();

                    String[] outputSplitted = output.split(",");
                    if (outputSplitted.length < 5)
                    {
                        System.out.println("[!] CHYBA! Soubor pro import je v nespravnem formatu!");

                        continueByPressingAnyKey();
                        return;
                    }

                    if (Objects.equals(outputSplitted[0], "Telekomunikace"))
                        students.put(students.size() + 1, new TelecommunicationsStudent(students.size() + 1, outputSplitted[1], outputSplitted[2], outputSplitted[3]));
                    else
                        students.put(students.size() + 1, new CybersecurityStudent(students.size() + 1, outputSplitted[1], outputSplitted[2], outputSplitted[3]));

                    for (int i = 4; i < outputSplitted.length; i++)
                    {
                        String gradeStr = outputSplitted[i];
                        gradeStr = gradeStr.replace("[", "");
                        gradeStr = gradeStr.replace("]", "");
                        gradeStr = gradeStr.replace(" ", "");

                        students.get(students.size()).addGrade(Integer.parseInt(gradeStr));
                    }

                }
                catch (Exception e)
                {
                    System.out.println("[!] CHYBA! Nepodarilo se otevrit / cist soubor pro import nebo jsou data v nespravnem formatu pro import!");
                    continueByPressingAnyKey();
                    return;
                }

                System.out.println("[>] USPECH! Student byl importovan ze souboru, jeho ID je: #" + students.size() + ".");

                continueByPressingAnyKey();
                return;

            case 11:
                System.out.println();
                System.out.println("--->   Moznost 11: Ukoncit program   <---");

                dbConn.pushToDb(students);

                System.out.println("[#] Program se ukoncuje...");
                return;

            default:
                System.out.println("[!] CHYBA! Vybrana moznost neexistuje.");
                continueByPressingAnyKey();
        }
    }

    private static void continueByPressingAnyKey()
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("[#] Pokracujte stisknutim jakekoliv klavesy...");
        scanner.nextLine();
        main(new String[]{"halabala"});
    }

    private static int getStudentIdFromInput(Scanner scanner, int currentStep, int totalSteps)
    {
        int id;
        System.out.print("[" + currentStep + "/" + totalSteps + "] Zadejte ID studenta: ");
        String idString = scanner.next();
        try
        {
            id = Integer.parseInt(idString);
        }
        catch (NumberFormatException e)
        {
            System.out.println("[!] CHYBA! ID studenta musi byt cele cislo!");

            continueByPressingAnyKey();
            return -1;
        }
        if (!students.containsKey(id))
        {
            System.out.println("[!] CHYBA! Student s timto ID nebyl nalezen!");

            continueByPressingAnyKey();
            return -1;
        }

        return id;
    }
}