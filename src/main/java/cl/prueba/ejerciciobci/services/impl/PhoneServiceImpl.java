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

    public List<PhoneDTO> updatePhonesUser(List<PhoneDTO> phones, Long userId) throws UserException {
        try{
            List<PhoneEntity> phonesEntityList = phoneRespository.findByUserId(userId);
            if(Objects.nonNull(phones) && phones.size()>0){
                List<PhoneEntity> newPhones = getNewPhonesUpdate(phonesEntityList, phones,userId);
                newPhones = phoneRespository.saveAll(newPhones);
                return getPhoneDTOListFromEntity(newPhones);
            }
            else{
                return getPhoneDTOListFromEntity(phonesEntityList);
            }
        }catch (UserException e){
            throw e;
        }catch(Exception e){
            throw new UserException(500, Constants.PHONES_UPDATE_ERROR);
        }
    }

    public List<PhoneEntity> getNewPhonesUpdate (List<PhoneEntity> phoneEntityList, List<PhoneDTO> phoneDTOS,Long userId) throws UserException {
        List<PhoneEntity> newPhones = getPhoneEntityListFromDTO(phoneDTOS,userId);
        List<PhoneEntity> updatePhones = numbersToUpdate(newPhones, phoneEntityList);
        List<PhoneEntity> deletePhones = numbersToDelete(newPhones,phoneEntityList);
        try{
            phoneRespository.deleteAll(deletePhones);
            return updatePhones;
        } catch(Exception e){
            throw new UserException(500,Constants.PHONES_DELETE_ERROR);
        }
    }

    public List<PhoneEntity> numbersToUpdate(List<PhoneEntity> newPhones, List<PhoneEntity> phonesDB){
       List<PhoneEntity> response = new ArrayList<>(newPhones);
        for (PhoneEntity np: response){
            for (PhoneEntity p:phonesDB) {
                if (isSameNumber(p,np)){
                    np.setId(p.getId());
                }
            }
        }
       return response;
    }

    boolean isSameNumber(PhoneEntity number1, PhoneEntity number2){
        if(number1.getNumber().equalsIgnoreCase(number2.getNumber())
                && number1.getCitycode().equalsIgnoreCase(number2.getCitycode())
                && number1.getContrycode().equalsIgnoreCase(number2.getContrycode())){
            return true;
        }
        return false;
    }

    public List<PhoneEntity> numbersToDelete(List<PhoneEntity> newPhones, List<PhoneEntity> phonesDB){
        List<PhoneEntity> response = new ArrayList<>();
        for (PhoneEntity p: phonesDB){
            boolean found=false;
            for (PhoneEntity np:newPhones) {
                if (isSameNumber(p,np)){
                    found=true;
                }
            }
            if (!found){
                response.add(p);
            }
        }
        return response;
    }




}
