package Models.Classes;

public class TelecommunicationsStudent extends Student implements Models.Interfaces.TelecommunicationsStudent {
    public TelecommunicationsStudent(int id, String name, String surname, String dateOfBirth)
    {
        super(id, name, surname, dateOfBirth);
    }

    @Override
    public String getNameInMorse() {
        return null;
    }
}
