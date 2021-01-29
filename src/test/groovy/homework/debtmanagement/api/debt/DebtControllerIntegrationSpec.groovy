package homework.debtmanagement.api.debt

import homework.debtmanagement.IntegrationSpec
import homework.debtmanagement.model.dto.in.DebtInDto
import homework.debtmanagement.model.dto.out.DebtDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared

import java.time.LocalDate

import static org.springframework.http.HttpStatus.*

@Transactional
class DebtControllerIntegrationSpec extends IntegrationSpec {

    @Autowired
    TestRestTemplate restTemplate

    @Shared
    Long debtId = 1l

    @Shared
    String eur = 'EUR'

    @Shared
    LocalDate futureDate = LocalDate.now().plusMonths(1)

    @Shared
    LocalDate pastDate = LocalDate.now().minusDays(1)

    @Shared
    BigDecimal amount100 = BigDecimal.valueOf(100)

    @Shared
    BigDecimal minus100 = BigDecimal.valueOf(-100)

    void 'should GET debt by customer and debt id'() {
        setup:
            String getDebtUri = "$CUSTOMERS_URI/$customerId/$DEBTS_URI/$debtId"
        when:
        ResponseEntity<DebtDto> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD).getForEntity(apiUrl(getDebtUri), DebtDto)
        then:
            with(response) {
                statusCode == OK
                with(body) {
                    id == debtId
                    amount
                    currency
                    dueDate
                    created
                    updated
                }
            }
    }

    void 'should validate when request debt by customer and debt id'() {
        setup:
            String getDebtUri = "$CUSTOMERS_URI/$customer/$DEBTS_URI/$debt"
        when:
            ResponseEntity<String> response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD).getForEntity(apiUrl(getDebtUri), String)
        then:
            response.statusCode == expectedStatusCode
        where:
            customer   | debt       || expectedStatusCode
            notExistId | debtId     || NOT_FOUND
            customerId | notExistId || NOT_FOUND
            customerId | debtId     || OK
    }

    void 'should GET all debts by customer id'() {
        setup:
            String getDebtsUri = "$CUSTOMERS_URI/$customerId/$DEBTS_URI"
        when:
            ResponseEntity<DebtDto[]> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD).getForEntity(apiUrl(getDebtsUri), DebtDto[])
        then:
            with(response) {
                statusCode == OK
                with(body) {
                    size() == 2
                    body.each {
                        with(it) {
                            id
                            customerId
                            amount
                            currency
                            dueDate
                            created
                            updated
                        }
                    }
                }
            }
    }

    void 'should POST and store debt'() {
        setup:
            DebtInDto debtInDto = new DebtInDto(
                BigDecimal.valueOf(500), eur, futureDate)
            String postDebtUri = "$CUSTOMERS_URI/3/$DEBTS_URI"
        when:
            ResponseEntity<Void> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .postForEntity(apiUrl(postDebtUri), debtInDto, Void)
        then:
            response.statusCode == OK
    }

    void 'should POST and validate request body'() {
        setup:
            DebtInDto debtInDto = DebtInDto.builder()
                    .amount(amount)
                    .currency(currency)
                    .dueDate(dueDate).build()
            String postDebtUri = "$CUSTOMERS_URI/$customer/$DEBTS_URI"
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .postForEntity(apiUrl(postDebtUri), debtInDto, String)
        then:
            with(response) {
                statusCode == expectedStatusCode
            }
        where:
            customer   | amount    | currency | dueDate    || expectedStatusCode
            notExistId | amount100 | eur      | futureDate || NOT_FOUND
            customerId | null      | eur      | futureDate || BAD_REQUEST
            customerId | minus100  | eur      | futureDate || BAD_REQUEST
            customerId | amount100 | 'EURO'   | futureDate || BAD_REQUEST
            customerId | amount100 | ''       | futureDate || BAD_REQUEST
            customerId | amount100 | eur      | pastDate   || BAD_REQUEST
            customerId | amount100 | eur      | null       || BAD_REQUEST

    }

    void 'should PUT, validate and update debt'() {
        setup:
            DebtInDto debtInDto = DebtInDto.builder()
                    .amount(amount100)
                    .currency(eur)
                    .dueDate(futureDate).build()
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            HttpEntity<String> entity = new HttpEntity<String>(debtInDto, headers)

            String updateUri = "$CUSTOMERS_URI/$customer/$DEBTS_URI/$debt"
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .exchange(apiUrl(updateUri).toURI(), HttpMethod.PUT, entity, String)
        then:
            response.statusCode == expectedStatusCode
        where:
            customer   | debt       || expectedStatusCode
            notExistId | debtId     || NOT_FOUND
            customerId | notExistId || NOT_FOUND
            customerId | debtId     || OK
    }

    void 'should DELETE, validate and delete debt'() {
        setup:
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            HttpEntity<String> entity = new HttpEntity<String>(headers)

            String deleteUri = "$CUSTOMERS_URI/$customer/$DEBTS_URI/$debt"
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .exchange(apiUrl(deleteUri).toURI(), HttpMethod.DELETE, entity, String)
        then:
            response.statusCode == expectedStatusCode
        where:
            customer   | debt       || expectedStatusCode
            notExistId | debtId     || NOT_FOUND
            customerId | notExistId || NOT_FOUND
            customerId | debtId     || OK
    }
}
