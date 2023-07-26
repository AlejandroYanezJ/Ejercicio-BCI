package cl.prueba.ejerciciobci.services.impl;

import cl.prueba.ejerciciobci.constants.ConstantError;
import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.dto.UserSignUpRequestDTO;
import cl.prueba.ejerciciobci.dto.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.entity.PhoneEntity;
import cl.prueba.ejerciciobci.entity.UserEntity;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.repository.PhoneRespository;
import cl.prueba.ejerciciobci.repository.UserRepository;
import cl.prueba.ejerciciobci.services.TokenService;
import cl.prueba.ejerciciobci.services.UserService;
import cl.prueba.ejerciciobci.utils.DateUtils;
import cl.prueba.ejerciciobci.utils.EncryptUtils;
import cl.prueba.ejerciciobci.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PhoneRespository phoneRespository;
    private final ValidationUtils validationUtils;

    public UserSignUpResponseDTO registerUser(UserSignUpRequestDTO user) throws Exception {
        validateUser(user);
        UserEntity userExist = userRepository.findByEmail(user.getEmail());
        if (!Objects.nonNull(userExist)){
            try{
                UserEntity entity = getDataGenerateUser(user);
                UserEntity insert = userRepository.save(entity);
                List<PhoneEntity> phones = getPhonesEntityFromArray(user.getPhones(),insert.getId());
                phoneRespository.saveAll(phones);
                user.setPassword(EncryptUtils.hiddenPassword(user.getPassword()));
                return UserSignUpResponseDTO.builder()
                        .user(user)
                        .id(entity.getUuid())
                        .created(entity.getCreated().toString())
                        .modified(entity.getModified().toString())
                        .lastLogin(entity.getLastLogin().toString())
                        .token(entity.getToken())
                        .isActive(entity.getIsActive())
                        .build();
            } catch (Exception e){
                throw new UserException(500,ConstantError.USR_ERROR);
            }
        }
        else{
            throw new UserException(409, ConstantError.EMAIL_EXIST);
        }
    }

    public UserEntity getDataGenerateUser(UserSignUpRequestDTO user){
        LocalDateTime actualDate = DateUtils.getCurrentDateTime();
        return UserEntity.builder()
                .uuid(UUID.randomUUID().toString())
                .name(user.getName())
                .email(user.getEmail())
                .password(EncryptUtils.encryptPassword(user.getPassword()))
                .created(actualDate)
                .modified(actualDate)
                .lastLogin(actualDate)
                .token(tokenService.generateToken(user.getEmail()))
                .isActive(true)
                .build();
    }

    public List<PhoneEntity> getPhonesEntityFromArray(ArrayList<PhoneDTO> phones, Long userId) {
        List<PhoneEntity> response = new ArrayList<>();
        if(Objects.nonNull(phones) && phones.size()>0){
            phones.stream().forEach(phone->{
                response.add(PhoneEntity.builder()
                        .userId(userId)
                        .citycode(phone.getCitycode())
                        .contrycode(phone.getContrycode())
                        .number(phone.getNumber())
                        .build());
            });
        }
        return response;
    }

    public void validateUser(UserSignUpRequestDTO user) throws UserException {
        validationUtils.validationUserRequest(user);
    }
}
