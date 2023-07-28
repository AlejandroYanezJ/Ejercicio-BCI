package cl.prueba.ejerciciobci.controller

import cl.prueba.ejerciciobci.dto.PhoneDTO
import cl.prueba.ejerciciobci.dto.UserDTO
import cl.prueba.ejerciciobci.dto.request.UserDeleteRequestDTO
import cl.prueba.ejerciciobci.dto.request.UserLoginRequestDTO
import cl.prueba.ejerciciobci.dto.request.UserUpdateRequestDTO
import cl.prueba.ejerciciobci.dto.response.UserDeleteResponseDTO
import cl.prueba.ejerciciobci.dto.response.UserLoginResponseDTO
import cl.prueba.ejerciciobci.dto.response.UserSignUpResponseDTO
import cl.prueba.ejerciciobci.dto.response.UserUpdateResponseDTO
import cl.prueba.ejerciciobci.services.UserService
import cl.prueba.ejerciciobci.utils.DateUtils
import spock.lang.Specification

import java.time.LocalDateTime

class UserControllerSpec extends Specification{

    UserService userService = Stub()
    UsersController usersController
    UserDTO userDTO
    UserSignUpResponseDTO userSignUpResponseDTO
    UserLoginRequestDTO userLoginRequestDTO
    UserLoginResponseDTO userLoginResponseDTO
    UserUpdateRequestDTO userUpdateRequestDTO
    UserUpdateResponseDTO userUpdateResponseDTO
    UserDeleteRequestDTO userDeleteRequestDTO
    UserDeleteResponseDTO userDeleteResponseDTO

    void setup(){
        usersController = new UsersController(userService)
        LocalDateTime localDate = DateUtils.getCurrentDateTime()
        PhoneDTO phoneDTO = new PhoneDTO("54360080","9","56")
        ArrayList<PhoneDTO> phoneDTOArrayList = Arrays.asList(phoneDTO)
        userDTO = new UserDTO("Alejandro Yañez", "alejandro.yanezj@utem.cl", "password123",phoneDTOArrayList)
        userSignUpResponseDTO = new UserSignUpResponseDTO(userDTO,"aaaa-aaaa-aaaa-aaaa",localDate,localDate,localDate,"token",true)
        userLoginRequestDTO = new UserLoginRequestDTO("alejandro.yanezj@utem.cl", "password1234")
        userLoginResponseDTO = new UserLoginResponseDTO("aaaa-aaaa-aaaa-aaaa",localDate,null,localDate,"token", true,"Alejandro Yañez", "alejandro.yanezj@utem.cl","password1234",phoneDTOArrayList)
        userUpdateRequestDTO = new UserUpdateRequestDTO("aaaa-aaaa-aaaa-aaaa","Alejandro Yañez Jaramillo",null,null,null,null)
        userUpdateResponseDTO = new UserUpdateResponseDTO(userDTO,"aaaa-aaaa-aaaa-aaaa",localDate,localDate,localDate,"token",true)
        userDeleteRequestDTO = new UserDeleteRequestDTO("aaaa-aaaa-aaaa-aaaa")
        userDeleteResponseDTO = new UserDeleteResponseDTO("Se borro el usuario", "aaaa-aaaa-aaaa-aaaa")
    }

    def "Se llama al controlador para crear un nuevo usuario"(){
        given:
        def user = userDTO
        userService.registerUser(_)>>userSignUpResponseDTO
        when:"se llama al metodo userSignUp en el controller"
        def response = usersController.userSignUp(user);
        then:
        Objects.nonNull(response.getBody().getId())
    }

    def "Se llama al controlador para obtener un usuario"(){
        given:
        def user = userLoginRequestDTO
        userService.loginUser(_) >>userLoginResponseDTO
        when: "Se llama al metodo login en el controller"
        def response = usersController.login(user)
        then:
        Objects.nonNull(response.getBody().getId())
    }

    def "Se llama al controlador para actualizar un usuario"(){
        given:
        def user = userUpdateRequestDTO
        def token = "token"
        userService.updateUser(_,_) >> userUpdateResponseDTO
        when: "Se llama al metodo updateUser en el controller"
        def response = usersController.updateUser(token,user)
        then:
        Objects.nonNull(response.getBody().getId())
    }

    def "Se llama al controlador para eliminar un usuario"(){
        given:
        def user = userDeleteRequestDTO
        def token = "token"
        userService.deleteUser(_,_) >> userDeleteResponseDTO
        when: "Se llama al metodo deleteUser en el controller"
        def response = usersController.deleteUser(token,user)
        then:
        Objects.nonNull(response.getBody().getId())
    }
}
