package Models.Interfaces;

import java.security.NoSuchAlgorithmException;

public interface CybersecurityStudent extends Student {
    String GetNameAsHash() throws NoSuchAlgorithmException;
}
