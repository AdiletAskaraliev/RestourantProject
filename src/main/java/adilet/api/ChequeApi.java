package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.ChequeRequest;
import adilet.dto.response.ChequeResponse;
import adilet.service.ChequeService;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;


@RestController
@RequestMapping("/api/cheques")
@RequiredArgsConstructor
public class ChequeApi {

    private final ChequeService chequeService;

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @PostMapping
    public SimpleResponse save(@RequestBody ChequeRequest request) {
        return chequeService.save(request);
    }

    @PermitAll
    @GetMapping
    public List<ChequeResponse> getAll() {
        return chequeService.getAll();
    }

    @PermitAll
    @GetMapping("/{id}")
    public ChequeResponse getById(@PathVariable Long id) {
        return chequeService.getById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id, @RequestBody @Valid ChequeRequest request) {
        return chequeService.update(id, request);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id) {
        return chequeService.deleteById(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','WAITER')")
    @GetMapping("/getByUser/{id}")
    public BigDecimal getByUser(@PathVariable Long id) {
        return chequeService.getAllChequesByUser(id);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping("/getByRest/{id}")
    public BigDecimal getByRest(@PathVariable Long id) {
        return chequeService.getAverageSum(id);
    }
}
