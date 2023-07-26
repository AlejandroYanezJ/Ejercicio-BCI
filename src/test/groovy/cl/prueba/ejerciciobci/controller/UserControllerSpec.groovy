package cl.prueba.ejerciciobci.controller

import cl.prueba.ejerciciobci.dto.PhoneDTO
import cl.prueba.ejerciciobci.dto.UserSignUpRequestDTO
import cl.prueba.ejerciciobci.dto.UserSignUpResponseDTO
import cl.prueba.ejerciciobci.services.UserService
import cl.prueba.ejerciciobci.utils.DateUtils
import spock.lang.Specification

import java.time.LocalDateTime

class UserControllerSpec extends Specification{

    UserService userService = Stub()
    UsersController usersController
    UserSignUpRequestDTO userSignUpRequestDTO
    UserSignUpResponseDTO userSignUpResponseDTO

    void setup(){
        usersController = new UsersController(userService)
        LocalDateTime localDate = DateUtils.getCurrentDateTime();
        PhoneDTO phoneDTO = new PhoneDTO("54360080","9","56");
        ArrayList<PhoneDTO> phoneDTOArrayList = Arrays.asList(phoneDTO);
        userSignUpRequestDTO = new UserSignUpRequestDTO("Alejandro Yañez", "alejandro.yanezj@utem.cl", "password123",phoneDTOArrayList)
        userSignUpResponseDTO = new UserSignUpResponseDTO(userSignUpRequestDTO,"aaaa-aaaa-aaaa-aaaa",localDate.toString(),localDate.toString(),localDate.toString(),"token",true)
    }

    def "Se llama al controlador para crear un nuevo usuario"(){
        given:
        def user = userSignUpRequestDTO
        userService.registerUser(_)>>userSignUpResponseDTO
        when:"se llama al metodo userSignUp en el controller"
        def response = usersController.userSignUp(user);
        then:
        Objects.nonNull(response.getBody())
    }
}
