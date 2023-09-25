package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.StopListRequest;
import adilet.dto.response.CategoryResponse;
import adilet.dto.response.PaginationCategory;
import adilet.dto.response.PaginationStopList;
import adilet.dto.response.StopListResponse;
import adilet.entity.MenuItem;
import adilet.entity.StopList;
import adilet.exception.AlreadyExistException;
import adilet.exception.NotFoundException;
import adilet.repository.MenuItemRepository;
import adilet.repository.StopListRepository;
import adilet.service.StopListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StopListServiceImpl implements StopListService {

    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;


    @Override
    public PaginationStopList findAll(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        Page<StopListResponse> stopLists = stopListRepository.findAllPagination(pageable);
        return PaginationStopList.builder()
                .stopListResponses(stopLists.getContent())
                .currentPage(stopLists.getNumber())
                .size(stopLists.getTotalPages())
                .build();
    }

    @Override
    public StopListResponse save(Long menuId, StopListRequest stopListRequest) {

        StopList stopList = new StopList();

        MenuItem menuItem = menuItemRepository.findById(menuId)
                .orElseThrow(() -> {
                    log.error("MenuItem with id: " + menuId + " not found");
                    return new NotFoundException("MenuItem with id: " + menuId + " not found");
                });

        boolean exists = stopListRepository.existsByDateAndMenuItem(stopListRequest.getDate(), menuItem);

        if (exists) {
            throw new AlreadyExistException("A StopList with the same date and meal already exists.");
        }

        stopList.setMenuItem(menuItem);
        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());

        stopListRepository.save(stopList);
        log.info("MenuItem successfully saved");
        return new StopListResponse(
                stopList.getReason(),
                stopList.getDate()
        );
    }


    @Override
    public StopListResponse findById(Long id) {
        return stopListRepository.findStopListById(id)
                .orElseThrow(() -> {
                    log.error("MenuItem with id: " + id + " not found");
                    return new NotFoundException("MenuItem with id: " + id + " not found");
                });
    }

    @Override
    public SimpleResponse update(Long id, StopListRequest stopListRequest) {
        StopList stopList = stopListRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("MenuItem with id: " + id + " not found");
                    return new NotFoundException("MenuItem with id: " + id + " not found");
                });

        stopList.setReason(stopListRequest.getReason());
        stopList.setDate(stopListRequest.getDate());
        stopListRepository.save(stopList);
        log.info("StopList successfully updated");
        return  SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        stopListRepository.deleteById(id);
        log.info("StopList successfully deleted");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }
}
