package homework.debtmanagement.api.debt

import homework.debtmanagement.model.dto.in.DebtInDto
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.*

class DebtControllerSpec extends Specification {

    @Shared
    Long customerId = 1l

    @Shared
    Long debtId = 1l

    @Shared
    DebtInDto debtInDto = makeDebtInDto(makeDebt(makeCustomer(customerId), debtId))

    DebtService debtService = Mock()

    @Subject
    DebtController debtController = new DebtController(debtService)

    void 'should call service when getDebt method invoked'() {
        when:
            debtController.getDebt(customerId, debtId)
        then:
            1 * debtService.getDebt(customerId, debtId)
    }

    void 'should call service when getAllDebtsByCustomer method invoked'() {
        when:
            debtController.getAllDebtsByCustomer(customerId)
        then:
            1 * debtService.getAllDebtsByCustomer(customerId)
    }

    void 'should call service when storeDebt method invoked'() {
        when:
            debtController.storeDebt(customerId, debtInDto)
        then:
            1 * debtService.storeDebt(customerId, debtInDto)
    }

    void 'should call service when updateDebt method invoked'() {
        when:
            debtController.updateDebt(customerId, debtId, debtInDto)
        then:
            1 * debtService.updateDebt(customerId, debtId, debtInDto)
    }

    void 'should call service when deleteDebt method invoked'() {
        when:
            debtController.deleteDebt(customerId, debtId)
        then:
            debtService.deleteDebt(customerId, debtId)
    }
}
