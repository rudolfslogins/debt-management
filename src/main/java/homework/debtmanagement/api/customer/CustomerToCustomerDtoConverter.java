package homework.debtmanagement.api.customer;

import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.dto.out.CustomerDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CustomerToCustomerDtoConverter implements Converter<Customer, CustomerDto>{

    @Override
    public CustomerDto convert(Customer source) {
        return CustomerDto.builder()
                .id(source.getId())
                .name(source.getName())
                .surname(source.getSurname())
                .country(source.getCountry())
                .email(source.getEmail())
                .password(source.getPassword())
                .created(source.getCreated())
                .updated(source.getUpdated())
                .build();
    }
}
