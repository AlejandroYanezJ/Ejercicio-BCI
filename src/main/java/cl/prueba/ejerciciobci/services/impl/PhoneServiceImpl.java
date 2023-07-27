package cl.prueba.ejerciciobci.services.impl;

import cl.prueba.ejerciciobci.constants.Constants;
import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.entity.PhoneEntity;
import cl.prueba.ejerciciobci.exception.UserException;
import cl.prueba.ejerciciobci.repository.PhoneRespository;
import cl.prueba.ejerciciobci.services.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PhoneServiceImpl implements PhoneService {

    private final PhoneRespository phoneRespository;

    public void savePhones(List<PhoneEntity> phones) throws UserException {
        try{
            phoneRespository.saveAll(phones);
        }catch (Exception e){
            throw new UserException(500, Constants.PHONES_INSERT_ERROR);
        }
    }

    public List<PhoneEntity> getPhoneEntityListFromDTO(List<PhoneDTO> phones, Long userId){
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

    public List<PhoneDTO> getPhonesUser(Long userId) throws UserException {
        try{
            List<PhoneEntity> phoneResult = phoneRespository.findByUserId(userId);
            return getPhoneDTOListFromEntity(phoneResult);
        } catch (Exception e){
            throw new UserException(500, Constants.PHONES_GET_ERROR);
        }
    }

    public List<PhoneDTO> getPhoneDTOListFromEntity(List<PhoneEntity> phones){
        List<PhoneDTO> response = new ArrayList<>();
        if (Objects.nonNull(phones) && phones.size()>0){
            phones.stream().forEach(phoneEntity->{
                response.add(PhoneDTO.builder()
                        .contrycode(phoneEntity.getContrycode())
                        .citycode(phoneEntity.getCitycode())
                        .number(phoneEntity.getNumber())
                        .build());
            });
        }
        return response;
    }

    public void deletePhonesFromUserId(Long userId) throws UserException {
        try{
            List<PhoneEntity> phones = phoneRespository.findByUserId(userId);
            phoneRespository.deleteAll(phones);
        }catch (Exception e){
            throw new UserException(500,Constants.PHONES_DELETE_ERROR);
        }

    }

    public List<PhoneDTO> updatePhonesUser(List<PhoneDTO> phones, Long userId) {
        return null;
    }


}
