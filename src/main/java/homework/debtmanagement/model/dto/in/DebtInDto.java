package homework.debtmanagement.model.dto.in;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Value
@Builder
public class DebtInDto {

    @NotNull(message = "CustomerId must not be null")
    Long customerId;

    @Positive(message = "Amount must be positive")
    @NotNull(message = "Amount must not be null")
    BigDecimal amount;

    @Size(min = 3, max = 3, message = "Currency must be 3 characters long")
    @NotEmpty(message = "Currency must not be empty")
    String currency;

    @FutureOrPresent(message = "Due date must be in present or future")
    @JsonFormat(pattern = "YYYY-MM-dd", shape = JsonFormat.Shape.STRING)
    LocalDate dueDate;

}
