package homework.debtmanagement.model;

import homework.debtmanagement.model.repository.CustomerRepository;
import homework.debtmanagement.model.repository.DebtRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class SeedData implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final DebtRepository debtRepository;

    public SeedData(CustomerRepository customerRepository, DebtRepository debtRepository) {
        this.customerRepository = customerRepository;
        this.debtRepository = debtRepository;
    }

    @Value("${seed-data:false}")
    private boolean seedData;

    @Override
    public void run(String... args) {
        loadSeedData();
    }

    private void loadSeedData() {
        if (customerRepository.count() == 0 && seedData) {
            Customer customer1 = createCustomer(
                    "John",
                    "Doe",
                    "USA",
                    "doe@test.com",
                    "abc123");

            Customer customer2 = createCustomer(
                    "Jeff",
                    "Cook",
                    "UK",
                    "cook@test.com",
                    "def456");

            Customer customer3 = createCustomer(
                    "Mickey",
                    "Jones",
                    "UK",
                    "mickey@test.com",
                    "ghi789");

            customerRepository.saveAll(List.of(customer1, customer2, customer3));

            Debt debt1 = createDebt(
                    customer1,
                    BigDecimal.valueOf(100.00),
                    "USD",
                    LocalDate.now().plusMonths(1));

            Debt debt2 = createDebt(
                    customer1,
                    BigDecimal.valueOf(200.00),
                    "USD",
                    LocalDate.now().plusMonths(2));

            Debt debt3 = createDebt(
                    customer2,
                    BigDecimal.valueOf(200.00),
                    "GBP",
                    LocalDate.now().plusMonths(1).plusWeeks(2));

            debtRepository.saveAll(List.of(debt1, debt2, debt3));

            System.out.println("Seed data loaded");
        }
    }

    private Customer createCustomer(String name, String surname, String country, String email, String password) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setSurname(surname);
        customer.setCountry(country);
        customer.setEmail(email);
        customer.setPassword(password);
        return customer;
    }

    private Debt createDebt(Customer customer, BigDecimal amount, String currency, LocalDate dueDate) {
        Debt debt = new Debt();
        debt.setCustomer(customer);
        debt.setAmount(amount);
        debt.setCurrency(currency);
        debt.setDueDate(dueDate);
        return debt;
    }
}
