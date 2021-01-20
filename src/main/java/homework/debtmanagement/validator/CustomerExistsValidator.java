package homework.debtmanagement.validator;

import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.dto.in.CustomerInDto;
import homework.debtmanagement.model.repository.CustomerRepository;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@AllArgsConstructor
public class CustomerExistsValidator implements ConstraintValidator<CustomerExistsConstraint, CustomerInDto> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean isValid(CustomerInDto value, ConstraintValidatorContext context) {
        Optional<Customer> customer = customerRepository.findFirstByNameAndSurnameAndCountryAndEmail(
                value.getName(), value.getSurname(), value.getCountry(), value.getEmail()
        );
        return customer.isEmpty();
    }
}
