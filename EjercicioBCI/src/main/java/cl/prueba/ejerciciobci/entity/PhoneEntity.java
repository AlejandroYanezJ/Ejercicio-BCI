package cl.prueba.ejerciciobci.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "PHONES")
@IdClass(value = PhoneEntity.IdClass.class)
@Builder
public class PhoneEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Long id;

    @Id
    @Column(name="CONTRYCODE")
    private String contrycode;
    @Id
    @Column(name="CITYCODE")
    private String citycode;

    @Id
    @Column(name="NUMBER")
    private String number;

    @Id
    @Column(name="USER_ID")
    private Long userId;

    @Data
    public static class IdClass implements Serializable {
        private Long id;
        private Long userId;
        private String number;
        private String citycode;
        private String contrycode;
    }
}
