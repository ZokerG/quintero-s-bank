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
@Table(name = "POCKET")
public class PocketRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private long bankAccountId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String pocketNumber;

    @Column(nullable = false)
    private double totalBalance;

    @Column(nullable = false)
    private LocalDate creatAt;
}
