package client.movieapp.PasswordSecurity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

// class to create password hashes
public class PasswordOptions {

    public static String passwordEncryptor(String password) throws NoSuchAlgorithmException {
        // make an instance of message digest and get its instance of the hashing algorithm md5
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        // create a byte array which has been hashed by passing it as bytes in the md5 function
        byte[] message = messageDigest.digest(password.toLowerCase().getBytes());
        // converting message to a big integer and then converting it back to a string
        BigInteger messageInt = new BigInteger(1, message);
        String encryptedPassword = messageInt.toString();
        return encryptedPassword;
    }

    // check if the password length is above 8 and has a special character
    public static boolean passwordValidator(String password) {
        String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,}$";
        // regex for validating passwords and returning true or false
        return Pattern.compile(regex).matcher(password).matches();
    }
}
