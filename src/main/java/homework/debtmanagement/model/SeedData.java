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
    public void run(String... args) throws Exception {
        loadSeedData();
    }

    private void loadSeedData() {
        if (customerRepository.count() == 0 && seedData) {
            Customer customer1 = new Customer();
            customer1.setName("John");
            customer1.setSurname("Doe");
            customer1.setCountry("USA");
            customer1.setEmail("doe@test.com");
            customer1.setPassword("abc123");

            Customer customer2 = new Customer();
            customer2.setName("Jeff");
            customer2.setSurname("Cook");
            customer2.setCountry("UK");
            customer2.setEmail("cook@test.com");
            customer2.setPassword("def456");

            customerRepository.saveAll(List.of(customer1, customer2));

            Debt debt1 = new Debt();
            debt1.setCustomer(customer1);
            debt1.setAmount(BigDecimal.valueOf(100.00));
            debt1.setCurrency("USD");
            debt1.setDueDate(LocalDate.now().plusMonths(1));

            Debt debt2 = new Debt();
            debt2.setCustomer(customer1);
            debt2.setAmount(BigDecimal.valueOf(200.00));
            debt2.setCurrency("USD");
            debt2.setDueDate(LocalDate.now().plusMonths(2));

            Debt debt3 = new Debt();
            debt3.setCustomer(customer2);
            debt3.setAmount(BigDecimal.valueOf(200.00));
            debt3.setCurrency("GBP");
            debt3.setDueDate(LocalDate.now().plusMonths(1).plusWeeks(2));

            debtRepository.saveAll(List.of(debt1, debt2, debt3));

            System.out.println("Seed data loaded");
        }
    }
}
