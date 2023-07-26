package cl.prueba.ejerciciobci.services.impl;

import cl.prueba.ejerciciobci.services.TokenService;
import cl.prueba.ejerciciobci.utils.DateUtils;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private static String SECRET = "test-jwt-secret";
    private static int EXPIRATION = 3600;

    public String generateToken(String email){
        LocalDateTime currentDatetime = DateUtils.getCurrentDateTime();
        Date issuedAt = DateUtils.toDate(currentDatetime);
        Date expiration = DateUtils.toDate(currentDatetime.plusSeconds(EXPIRATION));
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
}
