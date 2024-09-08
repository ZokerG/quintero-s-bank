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
@Table(name = "TRANSFER")
public class TransferRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long categoryId;

    @Column(nullable = false)
    private long bankAccountOriginId;

    @Column(nullable = false)
    private long bankAccountDestinationId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private LocalDate creatAt;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String type;
}
