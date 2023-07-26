package cl.prueba.ejerciciobci.repository;

import cl.prueba.ejerciciobci.entity.PhoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneRespository extends JpaRepository<PhoneEntity,Long> {

  /*  @Query("select p.id from PhoneEntity p where p.userId=?1 and p.citycode =?2 and p.contrycode =?3 and p.number=?4")
    List<PhoneEntity> findIdByAllAtributes(Long userId, int citycode, String contrycode, Long number);

    List<PhoneEntity> findAllByUserIdAndCitycodeAndContrycodeAndNumber(Long userId, int citycode, String contrycode, Long number);*/

}
