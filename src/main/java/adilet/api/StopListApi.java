package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.StopListRequest;
import adilet.dto.response.PaginationCategory;
import adilet.dto.response.PaginationStopList;
import adilet.dto.response.StopListResponse;
import adilet.service.StopListService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stop_lists")
@RequiredArgsConstructor
public class StopListApi {

    private final StopListService stopListService;

    @GetMapping
    public PaginationStopList getAll(@RequestParam int currentPage,
                                     @RequestParam int size){
        return stopListService.findAll(currentPage, size);
    }

    @PostMapping("/{menuId}")
    public StopListResponse save(@PathVariable Long menuId,
                                 @RequestBody StopListRequest stopListRequest){
        return stopListService.save(menuId, stopListRequest);
    }

    @GetMapping("/{id}")
    public StopListResponse findById(@PathVariable Long id){
        return stopListService.findById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody StopListRequest stopListRequest){
        return stopListService.update(id, stopListRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return stopListService.delete(id);
    }

}
