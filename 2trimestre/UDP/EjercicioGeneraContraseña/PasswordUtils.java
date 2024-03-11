import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PasswordUtils {

    // Método para generar una única contraseña (ya proporcionado)
    public static String generatePassword(String input) {
        StringBuilder password = new StringBuilder();
        Random random = new Random();
        String specialChars = "*$%&()-_";

        for (char c : input.toCharArray()) {
            // Transformación de caracteres y manejo de mayúsculas/minúsculas
            if (random.nextBoolean()) {
                switch (c) {
                    case 'o':
                        c = '0';
                        break;
                    case 'i':
                        c = '1';
                        break;
                    case 'a':
                        c = '4';
                        break;
                    case 'e':
                        c = '3';
                        break;
                    case 't':
                        c = '7';
                        break;
                    default:
                        break;
                }
            }
            if (random.nextBoolean()) {
                c = Character.toUpperCase(c);
            } else {
                c = Character.toLowerCase(c);
            }
            password.append(c);
        }

        // Añadir caracteres especiales
        for (int i = 0; i < 2; i++) {
            int index = random.nextInt(specialChars.length());
            char specialChar = specialChars.charAt(index);
            password.append(specialChar);
        }
        return password.toString();
    }

    // Método nuevo para generar múltiples contraseñas
    public static List<String> generatePasswords(String input, int numberOfPasswords) {
        List<String> passwords = new ArrayList<>();
        for (int i = 0; i < numberOfPasswords; i++) {
            passwords.add(generatePassword(input));
        }
        return passwords;
    }
}
