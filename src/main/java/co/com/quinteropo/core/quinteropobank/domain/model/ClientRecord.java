package co.com.quinteropo.core.quinteropobank.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT")
public class ClientRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    private String lastName;

    private String phone;

    @Column(unique = true)
    private String email;

    private String documentType;

    @Column(unique = true)
    private String document;

    private String province;

    private String city;

    private String password;

    private LocalDate creatAt;
}
