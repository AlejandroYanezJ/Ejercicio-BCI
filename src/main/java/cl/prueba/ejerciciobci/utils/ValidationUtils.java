package cl.prueba.ejerciciobci.utils;

import cl.prueba.ejerciciobci.constants.ConstantError;
import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.dto.UserSignUpRequestDTO;
import cl.prueba.ejerciciobci.exception.UserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ValidationUtils {

    @Value("${validation.regex.password}")
    String passwordRgx;
    @Value("${validation.regex.email}")
    String emailRgx;

    public void validationEmail(String email) throws UserException {
        Pattern pattern = Pattern.compile(emailRgx);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()){
            throw new UserException(400,ConstantError.INVALID_EMAIL);
        }
    }
    public void validationPassword(String password) throws UserException {
        Pattern pattern = Pattern.compile(passwordRgx);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()){
            throw new UserException(400, ConstantError.INVALID_PSW);
        }
    }

    public void validationUserRequest(UserSignUpRequestDTO user) throws UserException {
        if(!validationEmptyNull(user.getName())) throw new UserException(400,ConstantError.EMPTY_NAME);
        if(!validationEmptyNull(user.getEmail())) throw new UserException(400,ConstantError.EMPTY_EMAIL);
        validationEmail(user.getEmail());
        if(!validationEmptyNull(user.getPassword())) throw new UserException(400,ConstantError.EMPTY_PSW);
        validationPassword(user.getPassword());
        validationPhones(user.getPhones());
    }

    public void validationPhones(ArrayList<PhoneDTO> phones) throws UserException{
        for (PhoneDTO p: phones) {
            if(!validationEmptyNull(p.getNumber())) throw new UserException(400,ConstantError.EMPTY_PHONES_NUMBER);
            if(!validationEmptyNull(p.getCitycode())) throw new UserException(400,ConstantError.EMPTY_PHONES_CITYCODE);
            if(!validationEmptyNull(p.getContrycode())) throw new UserException(400,ConstantError.EMPTY_PHONES_CONTRYCODE);
        }
    }

    public boolean validationEmptyNull(String value){
        if(Objects.nonNull(value) && value.length()>0){
            return true;
        }
        return false;
    }
}
