package cl.prueba.ejerciciobci.utils

import cl.prueba.ejerciciobci.dto.PhoneDTO
import cl.prueba.ejerciciobci.dto.UserDTO
import spock.lang.Specification

class ValidationUtilsSpec extends Specification{

    ValidationUtils validationUtils

    UserDTO userSignUpRequestDTO

    void setup(){

        validationUtils = new ValidationUtils()
        if(!Objects.nonNull(validationUtils.emailRgx)) validationUtils.emailRgx="^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+\$"
        if(!Objects.nonNull(validationUtils.passwordRgx)) validationUtils.passwordRgx="^([A-Za-z@.&\\d]){8,12}\$"
        PhoneDTO phoneDTO = new PhoneDTO("54360080","9","56");
        ArrayList<PhoneDTO> phoneDTOArrayList = Arrays.asList(phoneDTO);
        userSignUpRequestDTO = new UserDTO("Alejandro Yañez", "alejandro.yanezj@utem.cl", "password123",phoneDTOArrayList)
    }

    def "Validacion de objeto para creacion de usuario"(){
        given:
        def user = userSignUpRequestDTO
        when:"Se llama al metodo para validar el usuario"
        validationUtils.validationUserSignUpRequest(user)
        then:
        void
    }
}
