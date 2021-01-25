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

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class DebtService {

    public final DebtRepository debtRepository;

    public final CustomerRepository customerRepository;

    public final ConversionService conversionService;

    DebtDto getDebt(Long customerId, Long debtId) {
        Debt debt = findDebt(customerId, debtId);
        return conversionService.convert(debt, DebtDto.class);
    }

    List<DebtDto> getAllDebtsByCustomer(Long customerID) {
        Customer customer = findCustomer(customerID);
        List<Debt> debts = debtRepository.findAllByCustomer(customer).orElse(Collections.emptyList());
        return debts.stream()
                .map(debt -> conversionService.convert(debt, DebtDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public void storeDebt(Long customerId, DebtInDto debtDto) {
        Customer customer = findCustomer(customerId);
        Debt debt = new Debt();
        debt.setCustomer(customer);
        debt.setAmount(debtDto.getAmount());
        debt.setCurrency(debtDto.getCurrency().toUpperCase());
        debt.setDueDate(debtDto.getDueDate());
        debtRepository.save(debt);
    }

    @Transactional
    public DebtDto updateDebt(Long customerId, Long debtId, DebtInDto debtDto) {
        Debt debt = findDebt(customerId, debtId);
        debt.setAmount(debtDto.getAmount());
        debt.setCurrency(debtDto.getCurrency());
        debt.setDueDate(debtDto.getDueDate());
        return conversionService.convert(debtRepository.save(debt), DebtDto.class);
    }

    @Transactional
    public void deleteDebt(Long customerId, Long debtId) {
        Debt debt = findDebt(customerId, debtId);
        debtRepository.delete(debt);
    }

    private Customer findCustomer(Long customerId) {
        return customerRepository.findFirstById(customerId)
                    .orElseThrow(new CustomerNotFoundException(customerId));
    }

    private Debt findDebt(Long customerId, Long debtId) {
        Customer customer = findCustomer(customerId);
        return debtRepository.findFirstByCustomerAndId(customer, debtId)
                .orElseThrow(new DebtNotFoundException(customerId, debtId));
    }
}
