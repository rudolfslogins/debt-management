package homework.debtmanagement.model.repository;

import homework.debtmanagement.model.Debt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DebtRepository extends JpaRepository<Debt, Long> {

    Optional<Debt> findFirstById(Long id);
}
