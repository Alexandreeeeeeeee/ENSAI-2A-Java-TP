import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Password {
    /**
     * Hashes the provided password using the SHA-256 algorithm.
     * 
     * @param password the password to be hashed
     * @return a hexadecimal string representing the hashed password
     * @throws RuntimeException if the SHA-256 algorithm is not available
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hashedBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Attempts a brute-force attack to find the 6-digit number
     * 
     * @param targetHash the target hash to match
     * @return the 6-digit number that matches, or null if no match is found
     */
    public static String bruteForce6Digit(String targetHash) {
        for(int i = 0; i < 1000000; i++){
            String mdp = String.format("%06d", i);
            if (hashPassword(mdp).equals(targetHash)){
                return mdp;
            }
        }
        return null;
    }

    /**
     * Checks if the given password is strong according to the following criteria:
     * 
     * <ul>
     * <li>Minimum length of 12 characters</li>
     * <li>At least one uppercase letter</li>
     * <li>At least one lowercase letter</li>
     * <li>At least one digit</li>
     * <li>No whitespace characters</li>
     * </ul>
     * 
     * @param password the password to check
     * @return true if the password is strong, false otherwise
     */
    public static boolean isStrongPassword(String password) {
        int longueur = password.length();
        if (longueur < 12){
            return false;
        }
        int nb_min = 0;
        int nb_maj = 0;
        int nb_digit = 0;
        int nb_white = 0;
        for (int i = 0; i < longueur; i++){
            if (Character.isUpperCase(password.charAt(i))){
                nb_maj++;
            }
            if (Character.isLowerCase(password.charAt(i))){
                nb_min++;
            }
            if (Character.isDigit(password.charAt(i))){
                nb_digit++;
            }
            if (Character.isWhitespace(password.charAt(i))){
                nb_white++;
            }
        }
        if (nb_min != 0 && nb_maj != 0 && nb_digit != 0 & nb_white == 0){
            return true;
        }
        return false;
    }

    /**
     * Checks the strength of multiple passwords and stores the results in a
     * HashMap.
     *
     * @param passwords An ArrayList of passwords to be checked.
     * @return A HashMap where each password is mapped to a Boolean value:
     *         true if the password is strong, false otherwise
     */
    public static HashMap<String, Boolean> checkPasswordsList(ArrayList<String> passwords) {
        HashMap<String, Boolean> strengths = new HashMap<>();
        for(String password : passwords){
            strengths.put(password, isStrongPassword(password));
        }
        return strengths;
    }

    /**
     * Generates a secure random password with at least:
     * <ul>
     * <li>1 uppercase letter</li>
     * <li>1 lowercase letter</li>
     * <li>1 digit</li>
     * <li>1 special character</li>
     * </ul>
     * 
     * @param nbCar The desired length of the password (minimum 4).
     * @return A randomly generated password that meets the security criteria.
     */
    public static String generatePassword(int nbCar) {
        SecureRandom secureRandom = new SecureRandom();
        int restant = nbCar;
        int nb_min = secureRandom.nextInt(restant + 1);
        if (nb_min == restant){
            nb_min = nb_min - 3;
        }
        if (nb_min == 0){
            nb_min = 1;
        }
        restant = restant - nb_min;
        int nb_maj = secureRandom.nextInt(restant + 1);
        if (nb_maj == restant){
            nb_maj = nb_maj - 2;
        }
        if (nb_maj == 0){
            nb_maj = 1;
        }
        restant = restant - nb_maj;
        int nb_digit = secureRandom.nextInt(restant + 1);
        if (nb_digit == restant){
            nb_digit = nb_digit - 1;
        }
        if (nb_digit == 0){
            nb_digit =1;
        }
        int nb_spec = restant - nb_digit;
        
        List<String> lettres = List.of("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m","n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
        List<String> chiffres = List.of("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");
        List<String> speciaux = List.of("#", "!", "?", "(", ")", "{", "}", "[", "]", "_", "&", "@");

        for(int i = 0; i<= nb_min; )


        // Code here

        return null;
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            // No arguments provided, running all questions
            for (int i = 1; i <= 4; i++)
                runQuestion(String.valueOf(i));
        } else {
            for (String arg : args) {
                runQuestion(arg);
            }
        }
    }

    private static void runQuestion(String questionNumber) {

        System.out.println("\nQ" + questionNumber + "\n" + "-".repeat(20));
        switch (questionNumber) {
            case "1":
                String HashedPwd = "a97755204f392b4d8787b38d898671839b4a770a864e52862055cdbdf5bc5bee";
                String bruteForcedPwd = bruteForce6Digit(HashedPwd);
                System.out.println(bruteForcedPwd != null ? bruteForcedPwd : "No result found");
                break;

            case "2":
                System.out.println("Abc5          -> " + isStrongPassword("1234"));
                System.out.println("abcdef123456  -> " + isStrongPassword("abcdef123456"));
                System.out.println("AbCdEf123456  -> " + isStrongPassword("AbCdEf123456"));
                System.out.println("AbCdEf 123456 -> " + isStrongPassword("AbCdEf 123456"));
                break;

            case "3":
                ArrayList<String> passwords = new ArrayList<>(
                        List.of("Abc5", "abcdef123456", "AbCdEf123456", "AbCdEf 123456"));
                HashMap<String, Boolean> password_map = checkPasswordsList(passwords);

                if (password_map != null) {
                    for (Map.Entry<String, Boolean> entry : password_map.entrySet()) {
                        System.out.println(entry.getKey() + " -> " + entry.getValue());
                    }
                }
                break;

            case "4":
                System.out.println("Generated password: " + generatePassword(12));
                break;

            default:
                System.out.println("Invalid question number: " + questionNumber);
        }
    }

}
