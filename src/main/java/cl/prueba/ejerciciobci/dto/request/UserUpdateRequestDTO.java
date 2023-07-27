package cl.prueba.ejerciciobci.dto.request;

import cl.prueba.ejerciciobci.dto.PhoneDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequestDTO {

    private String id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    private List<PhoneDTO> phones;
}
