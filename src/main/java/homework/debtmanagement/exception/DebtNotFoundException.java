package homework.debtmanagement.exception;

import java.util.function.Supplier;

public class DebtNotFoundException extends RuntimeException implements Supplier<DebtNotFoundException> {

    public DebtNotFoundException() { super(); }
    public DebtNotFoundException(final Long debtId) {
        super(String.format("Debt with id:%s not found!", debtId));
    }
    public DebtNotFoundException(final Throwable clause) {super(clause);}
    public DebtNotFoundException(final String message, final Throwable clause) {super(message, clause);}

    @Override
    public DebtNotFoundException get() { return this; }

}
