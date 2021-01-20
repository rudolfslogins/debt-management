package homework.debtmanagement.exception;

import java.util.function.Supplier;

public class CustomerNotFoundException extends RuntimeException implements Supplier<CustomerNotFoundException> {

    public CustomerNotFoundException() { super(); };
    public CustomerNotFoundException(final Long customerId) {
        super(String.format("Customer with id:%s not found!", customerId));
    }
    public CustomerNotFoundException(final Throwable cause) { super(cause); }
    public CustomerNotFoundException(final String message, final Throwable cause) { super(message, cause); }

    @Override
    public CustomerNotFoundException get() {
        return this;
    }

}
