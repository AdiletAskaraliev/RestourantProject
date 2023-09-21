package adilet.repository;

import adilet.dto.response.CategoryResponse;
import adilet.dto.response.SubcategoryResponse;
import adilet.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<CategoryResponse> findCategoriesById(Long id);

    @Query("select new adilet.dto.response.CategoryResponse(c.id, c.name)" +
            "from Category c")
    Page<CategoryResponse> findAllPagination(Pageable pageable);

}