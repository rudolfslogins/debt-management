package homework.debtmanagement.model.dto.out;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class CustomerDto {

    Long id;
    String name;
    String surname;
    String country;
    String email;
    String password;
    LocalDateTime created;
    LocalDateTime updated;

}
