package cl.prueba.ejerciciobci.services.impl;

import cl.prueba.ejerciciobci.constants.Constants;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.services.TokenService;
import cl.prueba.ejerciciobci.utils.DateUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    private static String SECRET = "test-jwt-secret";
    private static int EXPIRATION = 3600;

    private static final String BEARER = "Bearer ";

    public String generateToken(String uuid){
        LocalDateTime currentDatetime = DateUtils.getCurrentDateTime();
        Date issuedAt = DateUtils.toDate(currentDatetime);
        Date expiration = DateUtils.toDate(currentDatetime.plusSeconds(EXPIRATION));
        return Jwts.builder()
                .setSubject(uuid)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public void validateToken(String token, String uuid) throws UserException {
        try{
            String parseToken = extractJwt(token);
            Claims claims = getAllClaimsFromToken(parseToken);
            String subject = claims.getSubject();
            if(!subject.equals(uuid)){
                throw new Exception();
            }
        }catch (Exception e){
            throw new UserException(401, Constants.INVALID_TOKEN);
        }
    }

    private String extractJwt(String token) throws Exception {
        if(!token.startsWith(BEARER))
            throw new Exception();
        return token.substring(BEARER.length());
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }
}
