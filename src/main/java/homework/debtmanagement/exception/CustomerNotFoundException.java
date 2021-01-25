package homework.debtmanagement.exception;

import java.util.function.Supplier;

public class CustomerNotFoundException extends RuntimeException implements Supplier<CustomerNotFoundException> {

    public CustomerNotFoundException(final Long customerId) {
        super(String.format("Customer with id:%s not found!", customerId));
    }

    @Override
    public CustomerNotFoundException get() {
        return this;
    }

}
