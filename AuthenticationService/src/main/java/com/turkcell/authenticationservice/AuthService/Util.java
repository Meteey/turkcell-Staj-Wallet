package com.turkcell.authenticationservice.AuthService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Util {
    public static String hashText(String text){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(text);
    }
    public static boolean checkHashedText(String text, String hashedText){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(text, hashedText);
    }
}
