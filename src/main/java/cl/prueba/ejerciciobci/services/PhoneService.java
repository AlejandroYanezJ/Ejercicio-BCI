package cl.prueba.ejerciciobci.services;

import cl.prueba.ejerciciobci.dto.PhoneDTO;
import cl.prueba.ejerciciobci.entity.PhoneEntity;
import cl.prueba.ejerciciobci.exception.UserException;

import java.util.List;

public interface PhoneService {

    public void savePhones(List<PhoneEntity> phones) throws UserException;

    public List<PhoneDTO> getPhonesUser(Long userId) throws UserException;

    public List<PhoneEntity> getPhoneEntityListFromDTO(List<PhoneDTO> phones, Long userId);

    public void deletePhonesFromUserId(Long userId) throws UserException;

    public List<PhoneDTO> updatePhonesUser(List<PhoneDTO> phones, Long userId);

    }
