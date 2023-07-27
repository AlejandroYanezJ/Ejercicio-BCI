package cl.prueba.ejerciciobci.services.impl;

import cl.prueba.ejerciciobci.constants.Constants;
import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.dto.request.UserDeleteRequestDTO;
import cl.prueba.ejerciciobci.dto.request.UserLoginRequestDTO;
import cl.prueba.ejerciciobci.dto.UserDTO;
import cl.prueba.ejerciciobci.dto.request.UserUpdateRequestDTO;
import cl.prueba.ejerciciobci.dto.response.UserDeleteResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserLoginResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserSignUpResponseDTO;
import cl.prueba.ejerciciobci.dto.response.UserUpdateResponseDTO;
import cl.prueba.ejerciciobci.entity.PhoneEntity;
import cl.prueba.ejerciciobci.entity.UserEntity;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.repository.UserRepository;
import cl.prueba.ejerciciobci.services.PhoneService;
import cl.prueba.ejerciciobci.services.TokenService;
import cl.prueba.ejerciciobci.services.UserService;
import cl.prueba.ejerciciobci.utils.DateUtils;
import cl.prueba.ejerciciobci.utils.EncryptUtils;
import cl.prueba.ejerciciobci.utils.ValidationUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final PhoneService phoneService;
    private final ValidationUtils validationUtils;

    public UserSignUpResponseDTO registerUser(UserDTO user) throws Exception {
        validationUtils.validationUserSignUpRequest(user);
        try{
            UserEntity userExist = userRepository.findByEmail(user.getEmail());
            if (!Objects.nonNull(userExist)){

                UserEntity entity = getDataGenerateUser(user);
                UserEntity insert = userRepository.save(entity);
                List<PhoneEntity> phones = phoneService.getPhoneEntityListFromDTO(user.getPhones(),insert.getId());
                phoneService.savePhones(phones);
                user.setPassword(insert.getPassword());
                return UserSignUpResponseDTO.builder()
                        .user(user)
                        .id(entity.getUuid())
                        .created(entity.getCreated())
                        .lastLogin(entity.getLastLogin())
                        .token(entity.getToken())
                        .isActive(entity.getIsActive())
                        .build();
            }
            else{
                throw new UserException(409, Constants.EMAIL_EXIST);
            }
        }catch (UserException e){
            throw e;
        }catch (Exception e){
            throw new UserException(500, Constants.USR_INSERT_ERROR);
        }
    }

    public UserLoginResponseDTO loginUser(UserLoginRequestDTO user) throws UserException {
        UserEntity userExist = userRepository.findByEmail(user.getEmail());
        validationUtils.validateUserLogin(userExist,user);
        UserEntity entityUpdate = getDataUpdateLoginUser(userExist);
        try {
            userRepository.save(entityUpdate);
            List<PhoneDTO> phones = phoneService.getPhonesUser(userExist.getId());
            return UserLoginResponseDTO.builder()
                    .id(entityUpdate.getUuid())
                    .created(entityUpdate.getCreated())
                    .lastLogin(entityUpdate.getLastLogin())
                    .modified(entityUpdate.getModified())
                    .token(entityUpdate.getToken())
                    .isActive(entityUpdate.getIsActive())
                    .name(entityUpdate.getName())
                    .email(entityUpdate.getEmail())
                    .password(user.getPassword())
                    .phones(phones)
                    .build();
        }catch (Exception e){
            throw new UserException(500, Constants.USR_INSERT_ERROR);
        }
    }

    public UserDeleteResponseDTO deleteUser(UserDeleteRequestDTO request,String token) throws UserException {
        tokenService.validateToken(token, request.getId());
        validationUtils.validationUuidFormat(request.getId());
        try{
            UserEntity user = userRepository.findByUuid(request.getId());
            if(Objects.nonNull(user)){
                phoneService.deletePhonesFromUserId(user.getId());
                userRepository.delete(user);
                return UserDeleteResponseDTO.builder()
                        .message(Constants.USER_DELETE_SUCCESS)
                        .id(request.getId())
                        .build();
            }else{
                throw new UserException(404, Constants.USER_NOT_FOUND_BY_ID);
            }
        }catch (UserException e){
            throw e;
        }catch (Exception e){
            throw new UserException(500, Constants.USR_DELETE_ERROR);
        }
    }

    public UserUpdateResponseDTO updateUser(UserUpdateRequestDTO user, String token)throws UserException{
        tokenService.validateToken(token, user.getId());
        validationUtils.validationUserUpdateRequest(user);
        try{
            UserEntity userExist = userRepository.findByEmail(user.getEmail());
            if (!Objects.nonNull(userExist) || user.getId().equals(userExist.getUuid())) {
                UserEntity entity = userRepository.findByUuid(user.getId());
                if(Objects.nonNull(entity)){
                    UserEntity newEntity = getDataUpdateUser(user, entity);
                    newEntity = userRepository.save(newEntity);
                    List<PhoneDTO> phones = phoneService.updatePhonesUser(user.getPhones(), entity.getId());

                    return UserUpdateResponseDTO.builder()
                            .user(getUserDTOFromEntityUpdate(newEntity, phones))
                            .id(newEntity.getUuid())
                            .created(newEntity.getCreated())
                            .modified(newEntity.getModified())
                            .lastLogin(newEntity.getLastLogin())
                            .token(newEntity.getToken())
                            .isActive(newEntity.getIsActive())
                            .build();
                }
                else{
                    throw new UserException(404,Constants.USER_NOT_FOUND_BY_ID);
                }
            }
            else{
                throw new UserException(409, Constants.EMAIL_EXIST);
            }
        }catch (UserException e){
            throw e;
        }catch (Exception e){
            throw new UserException(500, Constants.USR_UPDATE_ERROR);
        }
    }

    public UserEntity getDataGenerateUser(UserDTO user){
        LocalDateTime actualDate = DateUtils.getCurrentDateTime();
        String uuid = UUID.randomUUID().toString();
        return UserEntity.builder()
                .uuid(uuid)
                .name(user.getName())
                .email(user.getEmail())
                .password(EncryptUtils.encryptPassword(user.getPassword()))
                .created(actualDate)
                .modified(null)
                .lastLogin(actualDate)
                .token(tokenService.generateToken(uuid))
                .isActive(true)
                .build();
    }

    public UserEntity getDataUpdateLoginUser(UserEntity userExist){
        return UserEntity.builder()
                .id(userExist.getId())
                .uuid(userExist.getUuid())
                .name(userExist.getName())
                .email(userExist.getEmail())
                .password(userExist.getPassword())
                .created(userExist.getCreated())
                .modified(userExist.getModified())
                .lastLogin(DateUtils.getCurrentDateTime())
                .token(tokenService.generateToken(userExist.getUuid()))
                .isActive(userExist.getIsActive())
                .build();
    }

    public UserEntity getDataUpdateUser(UserUpdateRequestDTO user,UserEntity entityDB){
        String name = Objects.nonNull(user.getName())? user.getName(): entityDB.getName();
        String email = Objects.nonNull(user.getEmail())? user.getEmail():entityDB.getEmail();
        String password = Objects.nonNull(user.getPassword())? EncryptUtils.encryptPassword(user.getPassword()): entityDB.getPassword();
        Boolean isActive = Objects.nonNull(user.getIsActive())? user.getIsActive():entityDB.getIsActive();
        return UserEntity.builder()
                .id(entityDB.getId())
                .uuid(entityDB.getUuid())
                .name(name)
                .email(email)
                .token(tokenService.generateToken(entityDB.getUuid()))
                .password(password)
                .isActive(isActive)
                .modified(DateUtils.getCurrentDateTime())
                .created(entityDB.getCreated())
                .lastLogin(entityDB.getLastLogin())
                .build();
    }

    public UserDTO getUserDTOFromEntityUpdate(UserEntity entity, List<PhoneDTO> phones){
        return UserDTO.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .phones(phones)
                .build();
    }


}
