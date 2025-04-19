package Models.Classes;

import java.util.HashMap;

public class TelecommunicationsStudent extends Student implements Models.Interfaces.TelecommunicationsStudent {
    public TelecommunicationsStudent(int id, String name, String surname, String dateOfBirth)
    {
        super(id, name, surname, dateOfBirth);
    }

    HashMap<Character, String> morseCodes = new HashMap<>() {{
        put('A', ".–");
        put('B', "-...");
        put('C', "-.-.");
        put('D', "-..");
        put('E', ".");
        put('F', "..-.");
        put('G', "--.");
        put('H', "....");
        put('I', "..");
        put('J', ".---");
        put('K', "-.-");
        put('L', ".-..");
        put('M', "--");
        put('N', "-.");
        put('O', "---");
        put('P', ".--.");
        put('Q', "--.-");
        put('R', ".-.");
        put('S', "...");
        put('T', "-");
        put('U', "..-");
        put('V', "...-");
        put('W', ".--");
        put('X', "-..-");
        put('Y', "-.--");
        put('Z', "--..");
    }};
    @Override
    public String getNameInMorse() {
        StringBuilder returnValue = new StringBuilder();
        StringBuilder fullName = new StringBuilder();
        fullName.append(this.getName());
        fullName.append(" ");
        fullName.append(this.getSurname());
        for (int i = 0; i < fullName.length(); i++)
        {
            char letter = fullName.toString().toUpperCase().charAt(i);
            if (!morseCodes.containsKey(letter))
                returnValue.append(letter);
            else
                returnValue.append(morseCodes.get(letter)).append("/");
        }
        return returnValue.toString();
    }
}
