package homework.debtmanagement.api.customer;

import homework.debtmanagement.model.dto.in.CustomerInDto;
import homework.debtmanagement.model.dto.out.CustomerDetailedDto;
import homework.debtmanagement.model.dto.out.CustomerDto;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/customer", produces = APPLICATION_JSON_VALUE)
public class CustomerController {

    private final CustomerService customerService;

    @ApiOperation("Get customer by customer id")
    @GetMapping("{customerId}")
    public CustomerDto getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @ApiOperation("Get detailed customer info, including all customer debts, by customer id")
    @GetMapping("{customerId}/details")
    public CustomerDetailedDto getCustomerDetailed(@PathVariable Long customerId) {
        return customerService.getCustomerDetailed(customerId);
    }

    @ApiOperation("Get all customers")
    @GetMapping("all")
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @ApiOperation("Add new customer")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity storeCustomer(@RequestBody @Valid CustomerInDto customerDto) {
        customerService.storeCustomer(customerDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Change existing customer")
    @PutMapping(path = "{customerId}", consumes = APPLICATION_JSON_VALUE)
    public CustomerDto updateCustomer(
            @PathVariable Long customerId,
            @RequestBody @Valid CustomerInDto customerDto) {
        return customerService.updateCustomer(customerId, customerDto);
    }

    @ApiOperation("Delete customer by customer id")
    @DeleteMapping("{customerId}")
    public ResponseEntity deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.ok().build();
    }

}
