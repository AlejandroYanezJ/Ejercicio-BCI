package cl.prueba.ejerciciobci.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUpRequestDTO {
    private String name;
    private String email;
    private String password;
    private ArrayList<PhoneDTO> phones;

}
