package auth;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Генератор отвечает за генерацию соли и вычислению хэша.
 * Методы класса static - не требуют создания объекта класса
 * для использования.
 */
public class Generator {
    /**
     * Генерация соли. Соль возвращается в виде строки
     */
    public static String generateSalt() {
        final Random r = new SecureRandom();
        byte[] salt = new byte[32];
        r.nextBytes(salt);
        return String.format("%064x", new java.math.BigInteger(1, salt));
    }

   
    public static String generateHash(String password, String salt) {

        byte[] saltByteArray = hexStringToByteArray(salt);
        String saltForHash = "";
        try {
            saltForHash = new String(saltByteArray, "UTF-8");
        } catch (UnsupportedEncodingException uee) {
            uee.printStackTrace();
        }

        String text = password + saltForHash;
       
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
            try {
                md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
            } catch (UnsupportedEncodingException uee) {
                uee.printStackTrace();
            }
            byte[] digest = md.digest();
           
            return String.format("%064x", new java.math.BigInteger(1, digest));

        } catch (NoSuchAlgorithmException nsae) {
            nsae.printStackTrace();
        }
        return null;
    }

   
    private static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }
}
