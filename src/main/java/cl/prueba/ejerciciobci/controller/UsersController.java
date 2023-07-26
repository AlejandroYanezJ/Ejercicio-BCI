package cl.prueba.ejerciciobci.controller;

import cl.prueba.ejerciciobci.dto.UserSignUpRequestDTO;
import cl.prueba.ejerciciobci.dto.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PutMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserSignUpResponseDTO> userSignUp(@RequestBody UserSignUpRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.registerUser(request),null, HttpStatus.CREATED);
    }
}
