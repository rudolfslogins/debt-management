package homework.debtmanagement.model.dto.out;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime created;

    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime updated;

}
