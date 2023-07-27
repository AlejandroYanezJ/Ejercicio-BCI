package cl.prueba.ejerciciobci.dto.response;


import cl.prueba.ejerciciobci.dto.UserDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserSignUpResponseDTO {


    private UserDTO user;
    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;

}
