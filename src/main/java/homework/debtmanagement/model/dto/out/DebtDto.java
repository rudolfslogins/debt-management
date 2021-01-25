package homework.debtmanagement.model.dto.out;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Value
@Builder
public class DebtDto {

    Long id;
    Long customerId;
    BigDecimal amount;
    String currency;
    LocalDate dueDate;
    LocalDateTime created;
    LocalDateTime updated;

}
