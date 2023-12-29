package net.mhgoi.blog;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GenPassword {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "passphrase";
        System.out.println(passwordEncoder.encode(pass));
    }
}
