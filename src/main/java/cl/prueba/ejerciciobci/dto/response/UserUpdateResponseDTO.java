package cl.prueba.ejerciciobci.dto.response;

import cl.prueba.ejerciciobci.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateResponseDTO {

    private UserDTO user;
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;

}
