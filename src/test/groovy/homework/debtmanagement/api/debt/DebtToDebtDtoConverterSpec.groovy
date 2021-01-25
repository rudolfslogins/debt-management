package homework.debtmanagement.api.debt

import homework.debtmanagement.model.Debt
import homework.debtmanagement.model.dto.out.DebtDto
import spock.lang.Specification
import spock.lang.Subject

import static homework.debtmanagement.helper.ServicesHelper.makeCustomer
import static homework.debtmanagement.helper.ServicesHelper.makeDebt

class DebtToDebtDtoConverterSpec extends Specification {

    @Subject
    DebtToDebtDtoConverter debtDtoConverter = new DebtToDebtDtoConverter()

    void 'should convert from Debt to DebtDto object'() {
        given:
            Long customerId = 1l
            Long debtId = 1l
            Debt debt = makeDebt(makeCustomer(customerId), debtId)
        when:
            DebtDto result = debtDtoConverter.convert(debt)
        then:
            with(result) {
                id == debtId
                customerId == debt.customer.id
                amount == debt.amount
                currency == debt.currency
                dueDate == debt.dueDate
            }
    }
}
