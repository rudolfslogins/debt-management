package homework.debtmanagement.api.debt;

import homework.debtmanagement.exception.CustomerNotFoundException;
import homework.debtmanagement.exception.DebtNotFoundException;
import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.Debt;
import homework.debtmanagement.model.dto.in.DebtInDto;
import homework.debtmanagement.model.dto.out.DebtDto;
import homework.debtmanagement.model.repository.CustomerRepository;
import homework.debtmanagement.model.repository.DebtRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DebtService {

    public final DebtRepository debtRepository;

    public final CustomerRepository customerRepository;

    public final ConversionService conversionService;

    DebtDto getDebt(Long id) {
        Debt debt = findDebt(id);
        return conversionService.convert(debt, DebtDto.class);
    }

    List<DebtDto> getAllDebts() {
        List<Debt> debts = debtRepository.findAll();
        return debts.stream()
                .map(debt -> conversionService.convert(debt, DebtDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    void storeDebt(DebtInDto debtDto) {
        Customer customer = findCustomer(debtDto.getCustomerId());
        Debt debt = new Debt();
        debt.setCustomer(customer);
        debt.setAmount(debtDto.getAmount());
        debt.setCurrency(debtDto.getCurrency().toUpperCase());
        debt.setDueDate(debtDto.getDueDate());
        debtRepository.save(debt);
    }

    @Transactional
    DebtDto updateDebt(Long id, DebtInDto debtDto) {
        Debt debt = findDebt(id);
        if (!debtDto.getCustomerId().equals(debt.getCustomer().getId())) {
            debt.setCustomer(findCustomer(debtDto.getCustomerId()));
        }
        debt.setAmount(debtDto.getAmount());
        debt.setCurrency(debtDto.getCurrency());
        debt.setDueDate(debtDto.getDueDate());
        return conversionService.convert(debtRepository.save(debt), DebtDto.class);
    }

    @Transactional
    void deleteDebt(Long id) {
        Debt debt = findDebt(id);
        debtRepository.delete(debt);
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findFirstById(customerId)
                    .orElseThrow(new CustomerNotFoundException(customerId));
    }

    private Debt findDebt(Long id) {
        return debtRepository.findFirstById(id).orElseThrow(new DebtNotFoundException(id));
    }
}
