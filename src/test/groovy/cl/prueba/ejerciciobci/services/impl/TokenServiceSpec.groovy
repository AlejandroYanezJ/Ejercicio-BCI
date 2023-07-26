package cl.prueba.ejerciciobci.services.impl

import spock.lang.Specification

class TokenServiceSpec extends Specification{
    TokenServiceImpl tokenService
    String email
    void setup(){
        tokenService = new TokenServiceImpl();
        email = "alejandro.yanezj@utem.cl"
    }

    def "Obtener token"(){
        given:
        when:"Se llama a la funcion generateToken en el service"
        def token = tokenService.generateToken(email);
        then:
        Objects.nonNull(token)
    }
}
