package homework.debtmanagement.api.customer;

import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.dto.out.CustomerDetailedDto;
import homework.debtmanagement.model.dto.out.DebtDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class CustomerToCustomerDetailedDtoConverter implements Converter<Customer, CustomerDetailedDto> {

    @Override
    public CustomerDetailedDto convert(Customer source) {
        return CustomerDetailedDto.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .country(source.getCountry())
                .email(source.getEmail())
                .password(source.getPassword())
                .created(source.getCreated())
                .updated(source.getUpdated())
                .debts(source.getDebts().stream()
                        .map(debt -> DebtDto.builder()
                                .id(debt.getId())
                                .customerId(debt.getCustomer().getId())
                                .amount(debt.getAmount())
                                .currency(debt.getCurrency())
                                .dueDate(debt.getDueDate())
                                .created(debt.getCreated())
                                .updated(debt.getUpdated())
                                .build())
                        .collect(Collectors.toList())
                )
                .build();
    }
}
