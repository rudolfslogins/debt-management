package homework.debtmanagement.api.customer

import homework.debtmanagement.IntegrationSpec
import homework.debtmanagement.model.dto.in.CustomerInDto
import homework.debtmanagement.model.dto.out.CustomerDetailedDto
import homework.debtmanagement.model.dto.out.CustomerDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared

import static org.springframework.http.HttpStatus.*

@Transactional
class CustomerControllerIntegrationSpec extends IntegrationSpec {

    @Shared
    String usa = 'usa'

    @Shared
    String uk = 'uk'

    @Autowired
    TestRestTemplate restTemplate

    void 'should GET customer by id'() {
        setup:
            String getCustomerUri = "$CUSTOMERS_URI/$customerId"
        when:
            ResponseEntity<CustomerDto> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .getForEntity(apiUrl(getCustomerUri), CustomerDto)
        then:
            with(response) {
                statusCode == OK
                with(body) {
                    id == customerId
                    name
                    surname
                    country
                    email
                    password
                    created
                    updated
                }
            }
    }

    void 'should return NOT FOUND when no customer found by id'() {
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .getForEntity(apiUrl("$CUSTOMERS_URI/$uri"), String)
        then:
            with(response) {
                statusCode == NOT_FOUND
                body == "Customer with id:$notExistId not found!"
            }
        where:
            uri << ["$notExistId", "$notExistId/details"]
    }

    void 'should GET detailed customer info by id'() {
        setup:
            String getDetailedUri = "$CUSTOMERS_URI/$customerId/details"
        when:
            ResponseEntity<CustomerDetailedDto> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .getForEntity(apiUrl(getDetailedUri), CustomerDetailedDto)
        then:
            with(response) {
                statusCode == OK
                with(body) {
                    id == customerId
                    name
                    surname
                    country
                    email
                    password
                    created
                    updated
                    with(debts) {
                        size() == 2
                        debts.each {
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
    }

    void 'should GET all customers'() {
        setup:
            String getCustomersUri = CUSTOMERS_URI
        when:
            ResponseEntity<CustomerDto[]> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .getForEntity(apiUrl(getCustomersUri), CustomerDto[])
        then:
            print(response.body)
            with(response) {
                statusCode == OK
                with(body) {
                    size() == 3
                    body.each {
                        with(it) {
                            id
                            name
                            surname
                            country
                            email
                            password
                            created
                            updated
                    }
                    }
                }
            }
    }

    void 'should POST and store customer'() {
        setup:
            CustomerInDto customerInDto = new CustomerInDto(
                    'Darth', 'Vader', usa, 'darth@test.com', '123')
        when:
            ResponseEntity<Void> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .postForEntity(apiUrl(CUSTOMERS_URI), customerInDto, Void)
        then:
            response.statusCode == OK
    }

    void 'should POST and validate request body'() {
        setup:
            CustomerInDto customerInDto = CustomerInDto.builder()
                    .name(name)
                    .surname(surname)
                    .country(country)
                    .email(email)
                    .password(password).build()
        when:
            ResponseEntity<String> response = restTemplate
                .withBasicAuth(USERNAME, PASSWORD)
                .postForEntity(apiUrl(CUSTOMERS_URI), customerInDto, String)
        then:
            with(response) {
                statusCode == expectedStatusCode
            }
        where:
            name  | surname | country | email          | password || expectedStatusCode
            ''    | 'Sawyer'| usa     | 'tom@test.org' | 'qwe123' || BAD_REQUEST
            'Tom' | ''      | usa     | 'tom@test.org' | 'qwe123' || BAD_REQUEST
            'Tom' | 'Sawyer'| ''      | 'tom@test.org' | 'qwe123' || BAD_REQUEST
            'Tom' | 'Sawyer'| usa     | ''             | 'qwe123' || BAD_REQUEST
            'Tom' | 'Sawyer'| usa     | 'doe@test.com' | 'qwe123' || BAD_REQUEST
            'Tom' | 'Sawyer'| usa     | 'DOE@TEST.COM' | 'qwe123' || BAD_REQUEST
            'Tom' | 'Sawyer'| usa     | 'DOE@TEST.COM' | ''       || BAD_REQUEST
            'John'| 'Doe'   | usa     | 'doe@test.com' | 'qwe123' || BAD_REQUEST


    }

    void 'should PUT, validate and update customer'() {
        setup:
            CustomerInDto customerInDto = CustomerInDto.builder()
                    .name('Steve')
                    .surname(surname)
                    .country(uk)
                    .email(email)
                    .password('qwe123').build()
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            HttpEntity<String> entity = new HttpEntity<String>(customerInDto, headers)

            String updateUri = "$CUSTOMERS_URI/$customer"
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .exchange(apiUrl(updateUri).toURI(), HttpMethod.PUT, entity, String)
        then:
            response.statusCode == expectedStatusCode
        where:
            customer   | surname  | email             || expectedStatusCode
            notExistId | 'Sleeve' | 'sleeve@test.com' || NOT_FOUND
            2          | 'Sleeve' | 'doe@test.com'    || BAD_REQUEST
            2          | 'Sleeve' | 'sleeve@test.com' || OK
    }

    void 'should DELETE, validate and delete customer'() {
        setup:
            HttpHeaders headers = new HttpHeaders()
            headers.setContentType(MediaType.APPLICATION_JSON)
            HttpEntity<String> entity = new HttpEntity<String>(headers)

            String deleteUri = "$CUSTOMERS_URI/$customer"
        when:
            ResponseEntity<String> response = restTemplate
                    .withBasicAuth(USERNAME, PASSWORD)
                    .exchange(apiUrl(deleteUri).toURI(), HttpMethod.DELETE, entity, String)
        then:
            response.statusCode == expectedStatusCode
        where:
            customer   || expectedStatusCode
            notExistId || NOT_FOUND
            2          || OK
    }
}
