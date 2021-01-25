package homework.debtmanagement.exception;

import java.util.function.Supplier;

public class DebtNotFoundException extends RuntimeException implements Supplier<DebtNotFoundException> {

    public DebtNotFoundException(final Long debtId) {
        super(String.format("Debt with id:%s not found!", debtId));
    }
    public DebtNotFoundException(final Long customerId, final Long debtId) {
        super(String.format("Debt with id:%s for customer id:%s not found!", debtId, customerId));
    }

    @Override
    public DebtNotFoundException get() { return this; }

}
