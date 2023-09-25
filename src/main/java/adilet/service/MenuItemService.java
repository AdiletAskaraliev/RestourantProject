package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.MenuItemRequest;
import adilet.dto.response.MenuItemResponse;
import adilet.entity.MenuItem;

import java.util.List;
import java.util.Map;

public interface MenuItemService {
    List<MenuItem> findAll();

    MenuItemResponse save(Long resId, Long subcatId, MenuItemRequest menuItemRequest);

    MenuItemResponse findById(Long id);

    SimpleResponse update(Long id, MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long id);

    List<MenuItem> globalSearch(String searchTerm);


    List<MenuItemResponse> findMenuItemsSortedByPrice(String ascOrDesc);

    List<MenuItemResponse> filterIsVegan(Boolean trueOrFalse);
}
