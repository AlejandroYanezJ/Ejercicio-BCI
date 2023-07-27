package cl.prueba.ejerciciobci.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDeleteResponseDTO {

    String message;
    String id;
}
