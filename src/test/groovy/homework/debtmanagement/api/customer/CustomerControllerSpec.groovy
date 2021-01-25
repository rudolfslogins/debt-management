package homework.debtmanagement.api.customer

import homework.debtmanagement.model.dto.in.CustomerInDto
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.makeCustomer
import static homework.debtmanagement.helper.ServicesHelper.makeCustomerInDto

class CustomerControllerSpec extends Specification {

    @Shared
    Long customerId = 1l

    @Shared
    CustomerInDto customerInDto = makeCustomerInDto(makeCustomer(customerId))

    CustomerService customerService = Mock()

    @Subject
    CustomerController customerController = new CustomerController(customerService)

    void 'should call service when getCustomer method invoked'() {
        when:
            customerController.getCustomer(customerId)
        then:
            1 * customerService.getCustomer(customerId)
    }

    void 'should call service when getCustomerDetailed method invoked'() {
        when:
            customerController.getCustomerDetailed(customerId)
        then:
            1 * customerService.getCustomerDetailed(customerId)
    }

    void 'should call service when getAllCustomers method invoked'() {
        when:
            customerController.getAllCustomers()
        then:
            1 * customerService.getAllCustomers()
    }

    void 'should call service when storeCustomer method invoked'() {
        when:
            customerController.storeCustomer(customerInDto)
        then:
            1 * customerService.storeCustomer(customerInDto)
    }

    void 'should call service when updateCustomer method invoked'() {
        when:
            customerController.updateCustomer(customerId, customerInDto)
        then:
            1 * customerService.updateCustomer(customerId, customerInDto)
    }

    void 'should call service when deleteCustomer method invoked'() {
        when:
            customerController.deleteCustomer(customerId)
        then:
            customerService.deleteCustomer(customerId)
    }
}
