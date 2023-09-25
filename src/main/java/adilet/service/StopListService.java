package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.StopListRequest;
import adilet.dto.response.PaginationStopList;
import adilet.dto.response.StopListResponse;

public interface StopListService {

    PaginationStopList findAll(int currentPage, int size);

    StopListResponse save(Long menuId, StopListRequest stopListRequest);

    StopListResponse findById(Long id);

    SimpleResponse update(Long id, StopListRequest stopListRequest);

    SimpleResponse delete(Long id);
}
