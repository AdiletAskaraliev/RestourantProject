package adilet.repository;

import adilet.dto.response.RestaurantResponse;
import adilet.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select case when count (c) > 0 then true else false end from Restaurant c where c.name = :name")
    boolean existsByName(String name);

    Optional<RestaurantResponse> findRestaurantById(Long id);
}