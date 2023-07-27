package cl.prueba.ejerciciobci.services;

import cl.prueba.ejerciciobci.exception.UserException;

public interface TokenService {
    public String generateToken(String uuid);

    public void validateToken(String token, String uuid) throws UserException;

}
