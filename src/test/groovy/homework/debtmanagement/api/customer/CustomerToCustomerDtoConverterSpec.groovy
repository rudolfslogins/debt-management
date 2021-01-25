package homework.debtmanagement.api.customer

import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.dto.out.CustomerDto
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.makeCustomer

class CustomerToCustomerDtoConverterSpec extends Specification {

    @Subject
    CustomerToCustomerDtoConverter customerDtoConverter = new CustomerToCustomerDtoConverter()

    void 'should convert from Customer to CustomerDto object'() {
        given:
            Customer customer = makeCustomer(1l)
        when:
            CustomerDto result = customerDtoConverter.convert(customer)
        then:
            with(result) {
                id == customer.id
                name == customer.name
                surname == customer.surname
                country == customer.country
                email == customer.email
                password == customer.password
            }

    }
}
