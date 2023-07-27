package cl.prueba.ejerciciobci.dto.response;

import cl.prueba.ejerciciobci.dto.PhoneDTO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLoginResponseDTO {

    private String id;
    private LocalDateTime created;
    private LocalDateTime modified;
    private LocalDateTime lastLogin;
    private String token;
    private Boolean isActive;
    private String name;
    private String email;
    private String password;
    private List<PhoneDTO> phones;

}
