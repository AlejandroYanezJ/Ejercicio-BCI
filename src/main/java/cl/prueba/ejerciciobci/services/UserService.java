package cl.prueba.ejerciciobci.services;

import cl.prueba.ejerciciobci.dto.request.UserDeleteRequestDTO;
import cl.prueba.ejerciciobci.dto.request.UserLoginRequestDTO;
import cl.prueba.ejerciciobci.dto.UserDTO;
import cl.prueba.ejerciciobci.dto.request.UserUpdateRequestDTO;
import cl.prueba.ejerciciobci.dto.response.UserDeleteResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserLoginResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserUpdateResponseDTO;
import cl.prueba.ejerciciobci.exception.UserException;


public interface UserService {

    public UserSignUpResponseDTO registerUser(UserDTO user) throws Exception;
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO user) throws Exception;
    public UserDeleteResponseDTO deleteUser(UserDeleteRequestDTO id, String token) throws UserException;

    public UserUpdateResponseDTO updateUser(UserUpdateRequestDTO user, String token)throws UserException;

    }
