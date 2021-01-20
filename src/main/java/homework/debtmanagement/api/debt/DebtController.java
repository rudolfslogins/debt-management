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
@RequestMapping(value = "api/debt", produces = APPLICATION_JSON_VALUE)
public class DebtController {

    private final DebtService debtService;

    @ApiOperation("Get debt by debt id")
    @GetMapping("{debtId}")
    public DebtDto getDebt(@PathVariable Long debtId) {
        return debtService.getDebt(debtId);
    }

    @ApiOperation("Get all debts")
    @GetMapping("all")
    public List<DebtDto> getAllDebts() { return debtService.getAllDebts(); }

    @ApiOperation("Add new debt")
    @ApiImplicitParam(name = "date", value = "Required date format: YYYY-MM-DD", example = "YYYY-MM-DD")
    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity storeDebt(@RequestBody @Valid DebtInDto debtDto) {
        debtService.storeDebt(debtDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation("Change existing debt. Also possible to reassign to another customer")
    @PutMapping(path = "{debtId}", consumes = APPLICATION_JSON_VALUE)
    public DebtDto updateDebt(
            @PathVariable Long debtId,
            @RequestBody @Valid DebtInDto debtDto) {
        return debtService.updateDebt(debtId, debtDto);
    }

    @ApiOperation("Delete debt by debt id")
    @DeleteMapping("{debtId}")
    public ResponseEntity deleteDebt(@PathVariable Long debtId) {
        debtService.deleteDebt(debtId);
        return ResponseEntity.ok().build();
    }

}
