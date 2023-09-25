package adilet.repository;

import adilet.dto.response.MenuItemResponse;
import adilet.entity.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {


    Optional<MenuItemResponse> findMenuItemById(Long id);

    @Query("select m FROM MenuItem m " +
            "join m.subcategory s join s.category c " +
            "where m.name ilike %:searchTerm% or s.name ilike  %:searchTerm% or c.name ilike  %:searchTerm%")
    List<MenuItem> globalSearch(@Param("searchTerm") String searchTerm);

    List<MenuItemResponse> findAllByOrderByPriceAsc();

    List<MenuItemResponse> findAllByOrderByPriceDesc();
    @Query("select new adilet.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            "from MenuItem m where m.isVegetarian = true")
    List<MenuItemResponse> findAllByVegetarianTrue();

    @Query("select new adilet.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian)" +
            "from MenuItem m where m.isVegetarian = false")
    List<MenuItemResponse> findAllByVegetarianFalse();


}