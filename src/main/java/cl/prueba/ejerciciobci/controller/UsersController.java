package cl.prueba.ejerciciobci.controller;

import cl.prueba.ejerciciobci.dto.request.UserDeleteRequestDTO;
import cl.prueba.ejerciciobci.dto.request.UserLoginRequestDTO;
import cl.prueba.ejerciciobci.dto.UserDTO;
import cl.prueba.ejerciciobci.dto.request.UserUpdateRequestDTO;
import cl.prueba.ejerciciobci.dto.response.UserDeleteResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserLoginResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserUpdateResponseDTO;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UsersController {

    private final UserService userService;

    @PutMapping(value = "/sign-up", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserSignUpResponseDTO> userSignUp(@RequestBody UserDTO request) throws Exception {
        return new ResponseEntity<>(userService.registerUser(request),null, HttpStatus.CREATED);
    }

    @GetMapping(value = "/login", consumes = "application/json", produces = "application/json")
    public ResponseEntity <UserLoginResponseDTO> login( @RequestBody UserLoginRequestDTO request) throws Exception {
        return new ResponseEntity<>(userService.loginUser(request),null, HttpStatus.OK);
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserUpdateResponseDTO> updateUser(@RequestHeader(value = "Authorization") String token, @RequestBody UserUpdateRequestDTO request) throws UserException {
        return new ResponseEntity<>(userService.updateUser(request,token),null, HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete", consumes = "application/json", produces = "application/json")
    public ResponseEntity<UserDeleteResponseDTO> deleteUser(@RequestHeader(value = "Authorization") String token, @RequestBody UserDeleteRequestDTO request) throws UserException {
        return new ResponseEntity<>(userService.deleteUser(request, token),null, HttpStatus.OK);
    }

}
