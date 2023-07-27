package cl.prueba.ejerciciobci.services.impl

import cl.prueba.ejerciciobci.dto.PhoneDTO
import cl.prueba.ejerciciobci.dto.UserDTO
import cl.prueba.ejerciciobci.entity.UserEntity
import cl.prueba.ejerciciobci.exception.UserException
import cl.prueba.ejerciciobci.repository.PhoneRespository
import cl.prueba.ejerciciobci.repository.UserRepository
import cl.prueba.ejerciciobci.services.TokenService
import cl.prueba.ejerciciobci.utils.DateUtils
import cl.prueba.ejerciciobci.utils.ValidationUtils
import spock.lang.Specification

import java.time.LocalDateTime

class UserServiceSpec extends Specification{

    UserRepository userRepository = Stub()
    TokenService tokenService = Stub()
    PhoneRespository phoneRespository = Stub()
    ValidationUtils validationUtils = Stub()
    UserServiceImpl userService;
    UserDTO userSignUpRequestDTO
    UserEntity userEntity


    void setup(){
        userService = new UserServiceImpl(userRepository,tokenService,phoneRespository,validationUtils);
        LocalDateTime localDate = DateUtils.getCurrentDateTime();
        PhoneDTO phoneDTO = new PhoneDTO("54360080","9","56");
        ArrayList<PhoneDTO> phoneDTOArrayList = Arrays.asList(phoneDTO);
        userSignUpRequestDTO = new UserDTO("Alejandro Yañez", "alejandro.yanezj@utem.cl", "password123",phoneDTOArrayList)
        userEntity = new UserEntity(1L,"aaaa-aaaa-aaaa-aaaa","Alejandro Yañez","alejandro.yanezj@utem.cl","*********",localDate,localDate,localDate,"token jwt",true)

    }

    def "Registro de usuario ok"(){
        given: "Se intenta registrar un usuario"
        def user = userSignUpRequestDTO
        userRepository.findByEmail(_) >> null
        userRepository.save(_) >> userEntity
        when: "se llama al metodo registerUser de la clase service"
        def response = userService.registerUser(user);
        then:"se retorna un objeto de salida"
        Objects.nonNull(response)
    }

    def "Registro de usuario pero ya existe"(){
        given: "Se intenta registrar un usuario"
        def user = userSignUpRequestDTO
        userRepository.findByEmail(_) >> userEntity
        when: "se llama al metodo registerUser de la clase service"
        def response = userService.registerUser(user);
        then:"Se recibe una excepcion"
        thrown UserException
    }

    def "Registro de usuario pero arroja una excepcion"(){
        given: "Se intenta registrar un usuario"
        def user = userSignUpRequestDTO
        userRepository.findByEmail(_) >> null
        userRepository.save(_) >> {throw new Exception("Alguna excepcion")}
        when: "se llama al metodo registerUser de la clase service"
        def response = userService.registerUser(user);
        then:"Se recibe una excepcion"
        thrown UserException
    }

}
