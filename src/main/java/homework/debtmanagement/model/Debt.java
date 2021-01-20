package homework.debtmanagement.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "DEBTS")
public class Debt extends BaseEntity {

    @Id
    @GeneratedValue(generator = "DEBTS_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "DEBTS_SEQ", sequenceName = "DEBTS_SEQ", allocationSize = 1)
    @Column(name = "DEBT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY", length = 3, nullable = false)
    private String currency;

    @Column(name = "DUE_DATE", nullable = false)
    private LocalDate dueDate;

}
