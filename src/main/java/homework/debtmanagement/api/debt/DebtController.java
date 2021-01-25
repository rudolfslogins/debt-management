package homework.debtmanagement.api.debt;

import homework.debtmanagement.model.dto.in.DebtInDto;
import homework.debtmanagement.model.dto.out.DebtDto;
import io.swagger.annotations.ApiImplicitParam;
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
@RequestMapping(value = "api/customers/{customerId}/debts", produces = APPLICATION_JSON_VALUE)
public class DebtController {

    private final DebtService debtService;

    @ApiOperation("Get debt by customer and debt id")
    @GetMapping("{debtId}")
    public DebtDto getDebt(
            @PathVariable Long customerId,
            @PathVariable Long debtId) {
        return debtService.getDebt(customerId, debtId);
    }

    @ApiOperation("Get all debts by customer")
    @GetMapping()
    public List<DebtDto> getAllDebtsByCustomer(
            @PathVariable Long customerId) {
        return debtService.getAllDebtsByCustomer(customerId); }

    @ApiOperation("Add new debt")
    @ApiImplicitParam(name = "date", value = "Required date format: YYYY-MM-DD", example = "YYYY-MM-DD")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity storeDebt(
            @PathVariable Long customerId,
            @RequestBody @Valid DebtInDto debtDto) {
        debtService.storeDebt(customerId, debtDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Change existing debt")
    @PutMapping(path = "{debtId}", consumes = APPLICATION_JSON_VALUE)
    public DebtDto updateDebt(
            @PathVariable Long customerId,
            @PathVariable Long debtId,
            @RequestBody @Valid DebtInDto debtDto) {
        return debtService.updateDebt(customerId, debtId, debtDto);
    }

    @ApiOperation("Delete debt by debt id")
    @DeleteMapping("{debtId}")
    public ResponseEntity deleteDebt(
            @PathVariable Long customerId,
            @PathVariable Long debtId) {
        debtService.deleteDebt(customerId, debtId);
        return ResponseEntity.ok().build();
    }

}
