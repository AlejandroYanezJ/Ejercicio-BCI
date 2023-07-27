package cl.prueba.ejerciciobci.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneDTO {
    private String number;
    private String citycode;
    private String contrycode;
}
