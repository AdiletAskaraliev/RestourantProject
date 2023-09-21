package adilet.repository;

import adilet.dto.response.MenuItemResponse;
import adilet.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {


    Optional<MenuItemResponse> findMenuItemById(Long id);
}