package homework.debtmanagement.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@Table(name = "CUSTOMERS")
public class Customer extends BaseEntity {

    @Id
    @GeneratedValue(generator = "CUSTOMERS_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "CUSTOMERS_SEQ", sequenceName = "CUSTOMERS_SEQ", allocationSize = 1)
    @Column(name = "CUSTOMER_ID")
    private Long id;

    @Column(name = "NAME", length = 35, nullable = false)
    private String name;

    @Column(name = "SURNAME", length = 35, nullable = false)
    private String surname;

    @Column(name = "COUNTRY", length = 70, nullable = false)
    private String country;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Debt> debts;

}
