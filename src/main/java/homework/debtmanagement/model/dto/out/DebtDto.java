package homework.debtmanagement.model.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime created;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime updated;

}
