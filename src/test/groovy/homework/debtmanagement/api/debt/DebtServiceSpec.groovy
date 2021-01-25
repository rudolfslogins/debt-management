package homework.debtmanagement.api.debt

import homework.debtmanagement.exception.CustomerNotFoundException
import homework.debtmanagement.exception.DebtNotFoundException
import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.Debt
import homework.debtmanagement.model.dto.in.DebtInDto
import homework.debtmanagement.model.dto.out.DebtDto
import homework.debtmanagement.model.repository.CustomerRepository
import homework.debtmanagement.model.repository.DebtRepository
import org.springframework.core.convert.ConversionService
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.*

class DebtServiceSpec extends Specification {

    @Shared
    Long customerId = 1l

    @Shared
    Customer customer = makeCustomer(customerId)

    @Shared
    Debt debt = makeDebt(customer, 3l)

    @Shared
    DebtInDto debtInDto = makeDebtInDto(debt)

    DebtRepository debtRepository = Mock()

    CustomerRepository customerRepository = Mock()

    ConversionService conversionService = Mock()

    @Subject
    DebtService debtService = new DebtService(debtRepository, customerRepository, conversionService)

    void setupSpec() {
        customer = addDebtsToCustomer(2, customer)
    }

    void 'should search for debt by customer and debt id'() {
        given:
            Debt debt = customer.debts.first()
        when:
            debtService.getDebt(customerId, debt.id)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.findFirstByCustomerAndId(customer, debt.id) >> Optional.of(customer.debts.first())
            1 * conversionService.convert(debt, DebtDto) >> DebtDto.builder().build()
    }

    void 'should search for all customer debts by customer id'() {
        given:
            List<Debt> debts = customer.debts
        when:
            debtService.getAllDebtsByCustomer(customerId)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.findAllByCustomer(customer) >> Optional.of(debts)
            2 * conversionService.convert(_, DebtDto) >> DebtDto.builder().build()
    }

    void 'should store debt'() {
        when:
            debtService.storeDebt(customerId, debtInDto)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.save(_)
    }

    void 'should update debt'() {
        when:
            debtService.updateDebt(customerId, debt.id, debtInDto)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.findFirstByCustomerAndId(customer, debt.id) >> Optional.of(debt)
            1 * debtRepository.save(debt) >> debt
            1 * conversionService.convert(debt, DebtDto) >> DebtDto.builder().build()
    }

    void 'should delete debt'() {
        given:
            Debt debtToDelete = customer.debts.first()
        when:
            debtService.deleteDebt(customerId, debtToDelete.id)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.findFirstByCustomerAndId(customer, debtToDelete.id) >> Optional.of(debtToDelete)
            1 * debtRepository.delete(debtToDelete)
    }

    void 'should throw exception when customer not found'() {
        given:
            Long id = 3l
        when:
            debtService.getDebt(id, debt.id)
        then:
            1 * customerRepository.findFirstById(id) >> Optional.empty()
            thrown(CustomerNotFoundException)
    }

    void 'should throw exception when debt not found'() {
        given:
            Long id = 3l
        when:
            debtService.getDebt(customerId, id)
        then:
            1 * customerRepository.findFirstById(customerId) >> Optional.of(customer)
            1 * debtRepository.findFirstByCustomerAndId(customer, id) >> Optional.empty()
            thrown(DebtNotFoundException)
    }
}
