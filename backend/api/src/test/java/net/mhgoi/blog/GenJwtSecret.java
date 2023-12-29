package net.mhgoi.blog;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class GenJwtSecret {
    public static void main(String[] args) {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        String jwtSecret = Encoders.BASE64.encode(key.getEncoded());
        System.out.println(jwtSecret);
    }
}
