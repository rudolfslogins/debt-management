package homework.debtmanagement

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import spock.lang.Shared
import spock.lang.Specification

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@ActiveProfiles('test')
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Transactional
abstract class IntegrationSpec extends Specification {

    public static final String CUSTOMERS_URI = 'customers'

    public static final String DEBTS_URI = 'debts'

    public static final String USERNAME = 'user'

    public static final String PASSWORD = 'password'

    @Shared
    Long customerId = 1l

    @Shared
    Long notExistId = 10l

    @LocalServerPort
    int serverPort

    String apiUrl(String uri) {
        "http://localhost:$serverPort/api/$uri"
    }
}
