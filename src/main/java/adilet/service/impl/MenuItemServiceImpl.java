package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.MenuItemRequest;
import adilet.dto.response.MenuItemResponse;
import adilet.entity.MenuItem;
import adilet.entity.Restaurant;
import adilet.entity.Subcategory;
import adilet.exception.NotFoundException;
import adilet.repository.MenuItemRepository;
import adilet.repository.RestaurantRepository;
import adilet.repository.SubcategoryRepository;
import adilet.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuItemServiceImpl implements MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final SubcategoryRepository subcategoryRepository;

    @Override
    public List<MenuItem> findAll() {
        return menuItemRepository.findAll();
    }

    @Override
    public MenuItemResponse save(Long resId, Long subcatId, MenuItemRequest menuItemRequest) {
        Restaurant restaurant = restaurantRepository.findById(resId)
                .orElseThrow(() -> {
                    log.error("Restaurant with id: " + resId + " not found");
                    return new NotFoundException("Restaurant with id: " + resId + " not found");
                });
        Subcategory subcategory = subcategoryRepository.findById(subcatId)
                .orElseThrow(() -> {
                    log.error("Subcategory with id: " + subcatId + " not found");
                    return new NotFoundException("Subcategory with id: " + subcatId + " not found");
                });


        MenuItem menuItem = new MenuItem();
        menuItem.setRestaurant(restaurant);
        menuItem.setSubcategory(subcategory);

        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());

        menuItemRepository.save(menuItem);
        log.info("MenuItem successfully saved");

        return new MenuItemResponse(
                menuItem.getId(),
                menuItem.getName(),
                menuItem.getImage(),
                menuItem.getPrice(),
                menuItem.getDescription(),
                menuItem.isVegetarian()
        );
    }

    @Override
    public MenuItemResponse findById(Long id) {
        return menuItemRepository.findMenuItemById(id)
                .orElseThrow(() -> {
                    log.error("MenuItem with id: " + id + " not found");
                    return new NotFoundException("MenuItem with id: " + id + " not found");
                });
    }

    @Override
    public SimpleResponse update(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("MenuItem with id: " + id + " not found");
                    return new NotFoundException("MenuItem with id: " + id + " not found");
                });

        menuItem.setName(menuItemRequest.getName());
        menuItem.setImage(menuItemRequest.getImage());
        menuItem.setPrice(menuItemRequest.getPrice());
        menuItem.setDescription(menuItemRequest.getDescription());
        menuItem.setVegetarian(menuItemRequest.isVegetarian());
        log.info("MenuItem successfully update!!!");
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem successfully update!!!")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        menuItemRepository.deleteById(id);
        log.info("MenuItem successfully deleted");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }


}
