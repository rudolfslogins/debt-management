package homework.debtmanagement.api.customer

import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.dto.out.CustomerDetailedDto
import spock.lang.Specification

import static homework.debtmanagement.helper.ServicesHelper.addDebtsToCustomer
import static homework.debtmanagement.helper.ServicesHelper.makeCustomer

class CustomerToCustomerDetailedDtoConverterSpec extends Specification {

    CustomerToCustomerDetailedDtoConverter customerDetailedDtoConverter = new CustomerToCustomerDetailedDtoConverter()

    void 'should convert from Customer to CustomerDetailedDto object'() {
        given:
            Customer customer = makeCustomer(1l)
            customer = addDebtsToCustomer(2, customer)
        when:
            CustomerDetailedDto result = customerDetailedDtoConverter.convert(customer)
        then:
            with(result) {
                id == customer.id
                name == customer.name
                surname == customer.surname
                country == customer.country
                email == customer.email
                password == customer.password
                debts.size() == 2
                debts.each {
                    customer.debts.contains(it)
                }
            }
    }
}
