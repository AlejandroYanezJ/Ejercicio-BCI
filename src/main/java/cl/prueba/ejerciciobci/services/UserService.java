package cl.prueba.ejerciciobci.services;

import cl.prueba.ejerciciobci.dto.UserSignUpRequestDTO;
import cl.prueba.ejerciciobci.dto.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.exception.UserException;


public interface UserService {

    public UserSignUpResponseDTO registerUser(UserSignUpRequestDTO user) throws Exception;
}
