package adilet.repository;

import adilet.dto.response.StopListResponse;
import adilet.entity.MenuItem;
import adilet.entity.StopList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Optional;

public interface StopListRepository extends JpaRepository<StopList, Long> {

    @Query("select new adilet.dto.response.StopListResponse(s.reason, s.date)" +
            "from StopList s")
    Page<StopListResponse> findAllPagination(Pageable pageable);

    @Query("select new adilet.dto.response.StopListResponse(s.reason, s.date)" +
            "from StopList s where s.id = :id")
    Optional<StopListResponse> findStopListById(Long id);

    boolean existsByDateAndMenuItem(LocalDate date, MenuItem menuItem);
}
