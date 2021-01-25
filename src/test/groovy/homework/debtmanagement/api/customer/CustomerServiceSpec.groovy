package homework.debtmanagement.api.customer

import homework.debtmanagement.exception.CustomerNotFoundException
import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.dto.in.CustomerInDto
import homework.debtmanagement.model.dto.out.CustomerDetailedDto
import homework.debtmanagement.model.dto.out.CustomerDto
import homework.debtmanagement.model.repository.CustomerRepository
import org.springframework.core.convert.ConversionService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.makeCustomer
import static homework.debtmanagement.helper.ServicesHelper.makeCustomerInDto

class CustomerServiceSpec extends Specification {

    @Shared
    Long customerId = 1l

    @Shared
    Customer customer = makeCustomer(customerId)

    @Shared
    CustomerInDto customerInDto = makeCustomerInDto(customer)

    CustomerRepository customerRepository = Mock()

    ConversionService conversionService = Mock()

    @Subject
    CustomerService customerService = new CustomerService(customerRepository, conversionService)

    void 'should search for customer by id'() {
        when:
            customerService.getCustomer(customerId)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * conversionService.convert(customer, CustomerDto) >> CustomerDto.builder().build()
    }

    void 'should search for detailed customer by id'() {
        when:
            customerService.getCustomerDetailed(customerId)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * conversionService.convert(customer, CustomerDetailedDto) >> CustomerDetailedDto.builder().build()
    }

    void 'should search for all customers'() {
        setup:
            List<Customer> customers = List.of(customer, makeCustomer(2l))
        when:
            customerService.getAllCustomers()
        then:
            1 * customerRepository.findAll() >> customers
            2 * conversionService.convert(_, CustomerDto) >> CustomerDto.builder().build()
    }

    void 'should store customer'() {
        when:
            customerService.storeCustomer(customerInDto)
        then:
            1 * customerRepository.save(_)
    }

    void 'should update customer'() {
        when:
            customerService.updateCustomer(customerId, customerInDto)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * customerRepository.save(customer) >> customer
            1 * conversionService.convert(customer, CustomerDto) >> CustomerDto.builder().build()
    }

    void 'should delete customer'() {
        when:
            customerService.deleteCustomer(customerId)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * customerRepository.delete(customer)
    }

    void 'should throw exception when customer not found'() {
        given:
            Long id = 3l
        when:
            customerService.getCustomer(id)
        then:
            1 * customerRepository.findFirstById(id) >> Optional.empty()
            thrown(CustomerNotFoundException)
    }

}
