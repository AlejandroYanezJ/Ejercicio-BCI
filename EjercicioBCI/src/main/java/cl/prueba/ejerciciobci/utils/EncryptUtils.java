package cl.prueba.ejerciciobci.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncryptUtils {

    public static String encryptPassword(String password){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        encoder.encode(password);
        return encoder.encode(password);
    }

    public static String hiddenPassword(String password){
        return password.replaceAll(".","*");
    }
}
