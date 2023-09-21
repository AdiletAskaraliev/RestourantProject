package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.MenuItemRequest;
import adilet.dto.response.MenuItemResponse;
import adilet.entity.MenuItem;
import adilet.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menuitem")
@RequiredArgsConstructor
public class MenuItemApi {

    private final MenuItemService menuItemService;

    @GetMapping
    public List<MenuItem> getAll(){
        return menuItemService.findAll();
    }

    @PostMapping("/{resId}/{subcatId}")
    public MenuItemResponse save(@PathVariable Long resId,
                                 @PathVariable Long subcatId,
                                 @RequestBody @Valid MenuItemRequest menuItemRequest){
        return menuItemService.save(resId, subcatId, menuItemRequest);
    }

    @GetMapping("/{id}")
    public MenuItemResponse findById(@PathVariable Long id){
        return menuItemService.findById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                   @RequestBody MenuItemRequest menuItemRequest){
        return menuItemService.update(id, menuItemRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return menuItemService.delete(id);
    }

}
