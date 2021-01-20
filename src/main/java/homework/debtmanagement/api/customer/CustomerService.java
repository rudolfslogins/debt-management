package homework.debtmanagement.api.customer;

import homework.debtmanagement.exception.CustomerNotFoundException;
import homework.debtmanagement.model.Customer;
import homework.debtmanagement.model.dto.in.CustomerInDto;
import homework.debtmanagement.model.dto.out.CustomerDetailedDto;
import homework.debtmanagement.model.dto.out.CustomerDto;
import homework.debtmanagement.model.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    private final ConversionService conversionService;

    CustomerDto getCustomer(Long id) {
        Customer customer = findCustomer(id);
        return conversionService.convert(customer, CustomerDto.class);
    }

    CustomerDetailedDto getCustomerDetailed(Long id) {
        Customer customer = findCustomer(id);
        return conversionService.convert(customer, CustomerDetailedDto.class);
    }

    List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> conversionService.convert(customer, CustomerDto.class))
                .collect(Collectors.toList());
     }

     @Transactional
     void storeCustomer(CustomerInDto customerDto) {
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setSurname(customerDto.getSurname());
        customer.setCountry(customerDto.getEmail());
        customer.setEmail(customerDto.getEmail());
        customer.setPassword(customerDto.getPassword());
        customerRepository.save(customer);
     }

     @Transactional
     CustomerDto updateCustomer(Long id, CustomerInDto customerDto) {
         Customer customer = findCustomer(id);
         customer.setName(customerDto.getName());
         customer.setSurname(customerDto.getSurname());
         customer.setCountry(customerDto.getCountry());
         customer.setEmail(customerDto.getEmail());
         customer.setPassword(customerDto.getPassword());
         return conversionService.convert(customerRepository.save(customer), CustomerDto.class);
    }

    @Transactional
    void deleteCustomer(Long id) {
        Customer customer = findCustomer(id);
        customerRepository.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customerRepository.findFirstById(id)
                .orElseThrow(new CustomerNotFoundException(id));
    }

}
