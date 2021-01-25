package homework.debtmanagement.model.repository;

import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    Optional<Debt> findFirstByCustomerAndId(Customer customer, Long debtId);

    Optional<List<Debt>> findAllByCustomer(Customer customer);

}
