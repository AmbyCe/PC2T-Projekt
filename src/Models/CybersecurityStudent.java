package Models;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CybersecurityStudent extends Student implements ICybersecurityStudent {
    public CybersecurityStudent(int id, String name, String surname, String dateOfBirth)
    {
        super(id, name, surname, dateOfBirth);
    }

    @Override
    public String getNameAsHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String combinedNameAndSurname = this.getName() + " " + this.getSurname();
            byte[] nameAndSurnameAsBytes = combinedNameAndSurname.getBytes(StandardCharsets.UTF_8);
            byte[] hashedName = digest.digest(nameAndSurnameAsBytes);
            return bytesToHex(hashedName);
        }
        catch (NoSuchAlgorithmException e) {
            return "Neplatny algoritmus hashe";
        }
    }

    private String bytesToHex(byte[] hash)
    {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(0xff & hash[i]);
            if (hex.length() == 1)
                hexString.append("0");
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
