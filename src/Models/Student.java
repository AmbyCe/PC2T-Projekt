package Models;

import java.util.ArrayList;

public class Student implements IStudent, Comparable<Student> {
    private int id;
    private String name;
    private String surname;
    private String dateOfBirth;

    private ArrayList<Integer> grades = new ArrayList();

    public Student(int id, String name, String surname, String dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public void addGrade(int grade) {
        this.grades.add(grade);
    }

    @Override
    public float getAvgGrade() {
        int sum = 0;
        for (int grade : grades)
            sum += grade;
        return (float) sum / grades.size();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public ArrayList<Integer> getGrades() {
        return grades;
    }

    @Override
    public int compareTo(Student o) {
        if (this.surname.compareTo(o.surname) != 0)
            return this.surname.compareTo(o.surname);
        return this.id - o.id;
    }
}
