package cl.prueba.ejerciciobci.repository;

import cl.prueba.ejerciciobci.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

    UserEntity findByEmail(String email);

    UserEntity findByUuid(String uuid);
}
