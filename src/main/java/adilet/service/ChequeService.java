package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.ChequeRequest;
import adilet.dto.response.ChequeResponse;

import java.math.BigDecimal;
import java.util.List;

public interface ChequeService {
    SimpleResponse save(ChequeRequest request);
    List<ChequeResponse> getAll();
    ChequeResponse getById(Long id);
    SimpleResponse update(Long id, ChequeRequest request);
    SimpleResponse deleteById(Long id);
    BigDecimal getAllChequesByUser(Long userId);
    BigDecimal getAverageSum(Long restId);
}
