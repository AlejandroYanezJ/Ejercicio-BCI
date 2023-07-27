package cl.prueba.ejerciciobci.repository;

import cl.prueba.ejerciciobci.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRespository extends JpaRepository<PhoneEntity,Long> {

    List<PhoneEntity> findByUserId(Long userId);

}
