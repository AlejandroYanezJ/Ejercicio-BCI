package cl.prueba.ejerciciobci.utils;

import cl.prueba.ejerciciobci.constants.Constants;
import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.dto.request.UserLoginRequestDTO;
import cl.prueba.ejerciciobci.dto.UserDTO;
import cl.prueba.ejerciciobci.dto.request.UserUpdateRequestDTO;
import cl.prueba.ejerciciobci.entity.UserEntity;
import cl.prueba.ejerciciobci.exception.UserException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
            throw new UserException(400, Constants.INVALID_EMAIL);
        }
    }
    public void validationPassword(String password) throws UserException {
        Pattern pattern = Pattern.compile(passwordRgx);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()){
            throw new UserException(400, Constants.INVALID_PSW);
        }
    }

    public void validateUserLogin(UserEntity userExist, UserLoginRequestDTO user) throws UserException {
        if (!Objects.nonNull(userExist) ||
                !EncryptUtils.validatePassword(userExist.getPassword(),user.getPassword())) throw new UserException(401, Constants.USER_PSW_EMAIL_ERROR);
        if (!EncryptUtils.validatePassword(userExist.getPassword(),user.getPassword())) throw new UserException(401, Constants.USER_PSW_EMAIL_ERROR);
        if (!userExist.getIsActive()) throw new UserException(409, Constants.USER_INACTIVE);

    }

    public void validationUserSignUpRequest(UserDTO user) throws UserException {
        if(!validationEmptyNull(user.getName())) throw new UserException(400, Constants.EMPTY_NAME);
        if(!validationEmptyNull(user.getEmail())) throw new UserException(400, Constants.EMPTY_EMAIL);
        validationEmail(user.getEmail());
        if(!validationEmptyNull(user.getPassword())) throw new UserException(400, Constants.EMPTY_PSW);
        validationPassword(user.getPassword());
        validationPhones(user.getPhones());
    }

    public void validationPhones(List<PhoneDTO> phones) throws UserException {
        for (PhoneDTO p: phones) {
            if(!validationEmptyNull(p.getNumber())) throw new UserException(400, Constants.EMPTY_PHONES_NUMBER);
            if(!validationEmptyNull(p.getCitycode())) throw new UserException(400, Constants.EMPTY_PHONES_CITYCODE);
            if(!validationEmptyNull(p.getContrycode())) throw new UserException(400, Constants.EMPTY_PHONES_CONTRYCODE);
        }
    }

    public void validationUserUpdateRequest(UserUpdateRequestDTO user) throws UserException {
        validationUuidFormat(user.getId());
        if(Objects.nonNull(user.getEmail())) validationEmail(user.getEmail());
        if(Objects.nonNull(user.getPassword())) validationPassword(user.getPassword());
        if(Objects.nonNull(user.getPhones())) validationPhones(user.getPhones());
    }

    public boolean validationEmptyNull(String value){
        if(Objects.nonNull(value) && value.length()>0){
            return true;
        }
        return false;
    }

    public void validationUuidFormat(String uuid) throws UserException {
        String uuidRgx = "^([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})$";
        Pattern pattern = Pattern.compile(uuidRgx);
        Matcher matcher = pattern.matcher(uuid);
        if (!matcher.matches()){
            throw new UserException(400, Constants.INVALID_UUID);
        }
    }
}
