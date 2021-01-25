package homework.debtmanagement.model.dto.out;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder
public class CustomerDetailedDto {

    Long id;
    String name;
    String surname;
    String country;
    String email;
    String password;
    LocalDateTime created;
    LocalDateTime updated;

    List<DebtDto> debts;

}
