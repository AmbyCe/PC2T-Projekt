package Models;

import java.security.NoSuchAlgorithmException;

public interface ICybersecurityStudent extends IStudent {
    String getNameAsHash() throws NoSuchAlgorithmException;
}
