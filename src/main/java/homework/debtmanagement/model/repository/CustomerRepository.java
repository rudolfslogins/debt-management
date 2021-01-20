package homework.debtmanagement.model.repository;

import homework.debtmanagement.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findFirstById(Long id);

    Optional<Customer> findFirstByNameAndSurnameAndCountryAndEmail(
            String name, String surname, String country, String email);
}
