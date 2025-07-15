package financieraBlockchain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    private String previousHash;
    private String currentHash;

    private String companyName;
    private BigDecimal amount;
    private Double interestRate;
    private LocalDate dueDate;
}
