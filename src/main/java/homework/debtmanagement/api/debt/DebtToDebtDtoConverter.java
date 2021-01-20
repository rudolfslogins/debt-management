package homework.debtmanagement.api.debt;

import homework.debtmanagement.model.Debt;
import homework.debtmanagement.model.dto.out.DebtDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DebtToDebtDtoConverter implements Converter<Debt, DebtDto> {

    @Override
    public DebtDto convert(Debt source) {
        return DebtDto.builder()
                .id(source.getId())
                .customerId(source.getCustomer().getId())
                .amount(source.getAmount())
                .currency(source.getCurrency())
                .dueDate(source.getDueDate())
                .created(source.getCreated())
                .updated(source.getUpdated())
                .build();
    }
}
