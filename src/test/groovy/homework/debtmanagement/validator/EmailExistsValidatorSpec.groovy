package homework.debtmanagement.validator

import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.dto.in.CustomerInDto
import homework.debtmanagement.model.repository.CustomerRepository
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.makeCustomer
import static homework.debtmanagement.helper.ServicesHelper.makeCustomerInDto

class EmailExistsValidatorSpec extends Specification {

    @Shared
    Customer customer = makeCustomer(1l)

    @Shared
    CustomerInDto customerInDto = makeCustomerInDto(customer)

    CustomerRepository customerRepository = Mock()

    @Subject
    EmailExistsValidator validator = new EmailExistsValidator(customerRepository)

    void 'should validate email already exists'() {
        when:
            Boolean validationResult = validator.isValid(customerInDto, null)
        then:
            1 * customerRepository.findFirstByEmailIgnoreCase(customer.email) >> repositoryResult
            validationResult == expectedValidationResult
        where:
            repositoryResult      || expectedValidationResult
            Optional.of(customer) || false
            Optional.empty()      || true
    }
}
