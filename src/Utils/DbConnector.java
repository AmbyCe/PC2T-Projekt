package Utils;

import Models.Classes.CybersecurityStudent;
import Models.Classes.Student;
import Models.Classes.TelecommunicationsStudent;

import java.sql.*;
import java.util.HashMap;

public class DbConnector {
    private Connection c = null;

    private void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:db/database.db");
            System.out.println("[>] Uspesne pripojeno k databazi...");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    private void disconnect() {
        try {
            c.close();
            System.out.println("[>] Uspesne odpojeno od databaze...");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables()
    {
        try {
            Statement stmt = c.createStatement();
            String sql = "DROP TABLE IF EXISTS students;";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = c.createStatement();
            sql = "DROP TABLE IF EXISTS grades;";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS students " +
                    "(id                INTEGER PRIMARY KEY     NOT NULL," +
                    " name              TEXT                NOT NULL, " +
                    " surname           INT                 NOT NULL, " +
                    " specialization    VARCHAR(100)           NOT NULL," +
                    " date_of_birth     VARCHAR(50)            );";
            stmt.executeUpdate(sql);
            stmt.close();

            stmt = c.createStatement();
            sql = "CREATE TABLE IF NOT EXISTS grades " +
                    "(id            INTEGER PRIMARY KEY AUTOINCREMENT       NOT NULL," +
                    " student_id    INT                                 NOT NULL, " +
                    " grade         INT                                 NOT NULL, " +
                    " FOREIGN KEY (student_id) REFERENCES students(id));";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public HashMap<Integer, Student> loadFromDb()
    {
        connect();
        HashMap<Integer, Student> students = new HashMap<>();

        ResultSet studentsSet;
        String sql = "SELECT * FROM students;";
        try (PreparedStatement prStmt = c.prepareStatement(sql)) {
            studentsSet = prStmt.executeQuery();

            while (studentsSet.next())
            {
                if (studentsSet.getString("specialization").equals("Telekomunikace"))
                    students.put(studentsSet.getInt("id"), new TelecommunicationsStudent(studentsSet.getInt("id"), studentsSet.getString("name"), studentsSet.getString("surname"), studentsSet.getString("date_of_birth")));
                else
                    students.put(studentsSet.getInt("id"), new CybersecurityStudent(studentsSet.getInt("id"), studentsSet.getString("name"), studentsSet.getString("surname"), studentsSet.getString("date_of_birth")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ResultSet gradesSet;
        sql = "SELECT * FROM grades;";
        try (PreparedStatement prStmt = c.prepareStatement(sql)) {
            gradesSet = prStmt.executeQuery();

            while (gradesSet.next())
            {
                students.get(gradesSet.getInt("student_id")).addGrade(gradesSet.getInt("grade"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[>] Vsichni studenti nacteni z databaze...");
        disconnect();

        return students;
    }

    public void pushToDb(HashMap<Integer, Student> students)
    {
        connect();
        createTables();
        students.forEach((k, v) -> {
            String sql = "INSERT INTO students " +
                    "(name,surname,specialization,date_of_birth) " +
                    "VALUES(?,?,?,?);";
            try (PreparedStatement prStmt = c.prepareStatement(sql)) {
                prStmt.setString(1, v.getName());
                prStmt.setString(2, v.getSurname());
                prStmt.setString(3, (v instanceof TelecommunicationsStudent) ? "Telekomunikace" : "Kyberbezpecnost");
                prStmt.setString(4, v.getDateOfBirth());

                prStmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }

            for (int grade : v.getGrades())
            {
                sql = "INSERT INTO grades (student_id,grade) VALUES (?,?);";
                try (PreparedStatement prStmt = c.prepareStatement(sql)) {
                    prStmt.setInt(1, v.getId());
                    prStmt.setInt(2, grade);

                    prStmt.executeUpdate();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            System.out.println("[>] Nahran student (#" + v.getId() + ") " + v.getName() + " " + v.getSurname() + "...");
        });
        disconnect();
    }
}
