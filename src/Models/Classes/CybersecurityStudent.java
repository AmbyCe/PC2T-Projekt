package Models.Classes;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class CybersecurityStudent extends Student implements Models.Interfaces.CybersecurityStudent {
    public CybersecurityStudent(int id, String name, String surname, String dateOfBirth)
    {
        super(id, name, surname, dateOfBirth);
    }

    @Override
    public String getNameAsHash() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String combinedNameAndSurname = this.getName() + " " + this.getSurname();
        byte[] nameAndSurnameAsBytes = combinedNameAndSurname.getBytes(StandardCharsets.UTF_8);
        byte[] hashedName = digest.digest(nameAndSurnameAsBytes);
        return hashedName.toString();
    }
}
