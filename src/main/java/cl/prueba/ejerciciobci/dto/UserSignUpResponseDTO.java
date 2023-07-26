package cl.prueba.ejerciciobci.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserSignUpResponseDTO {


    private UserSignUpRequestDTO user;
    private String id;
    private String created;
    private String modified;
    private String lastLogin;
    private String token;
    private Boolean isActive;

}
