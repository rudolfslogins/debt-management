package homework.debtmanagement.helper

import homework.debtmanagement.model.Customer
import homework.debtmanagement.model.Debt
import homework.debtmanagement.model.dto.in.CustomerInDto
import homework.debtmanagement.model.dto.in.DebtInDto

import java.time.LocalDate

import static org.apache.commons.lang3.RandomStringUtils.random

class ServicesHelper {

    static Customer makeCustomer(Long id) {
        new Customer(
                id: id,
                name: random(7, true, false),
                surname: random(7, true, false),
                country: 'uk',
                email: random(10, true, true) + '@test.com',
                password: random(10))
    }

    static CustomerInDto makeCustomerInDto(Customer customerIn) {
        CustomerInDto.builder()
                .name(customerIn.name)
                .surname(customerIn.surname)
                .country(customerIn.country)
                .email(customerIn.email)
                .password(customerIn.password)
                .build()
    }

    static Debt makeDebt(Customer customer, Long debtId) {
        new Debt(
                id: debtId,
                customer: customer,
                amount: Math.abs(new Random().nextInt() % 600 + 1),
                currency: 'EUR',
                dueDate: LocalDate.now().plusMonths(debtId))
    }

    static DebtInDto makeDebtInDto(Debt debt) {
        DebtInDto.builder()
                .amount(debt.amount)
                .currency(debt.currency)
                .dueDate(debt.dueDate)
                .build()
    }

    static Customer addDebtsToCustomer(int debtCount, Customer customer) {
        List<Debt> debts = []
        debtCount.times {
            debts << makeDebt(customer, it + 1)
        }
        customer.debts = debts
        customer
    }

}
