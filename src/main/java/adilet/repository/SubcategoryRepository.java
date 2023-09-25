package adilet.repository;

import adilet.dto.response.SubcategoryGroupResponse;
import adilet.dto.response.SubcategoryResponse;
import adilet.entity.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubcategoryRepository extends JpaRepository<Subcategory, Long> {

    Optional<SubcategoryResponse> findSubcategoriesById(Long id);

    @Query("select new adilet.dto.response.SubcategoryResponse(s.id, s.name) " +
            "from Subcategory s where s.category.id = :id order by s.name")
    List<SubcategoryResponse> findSubcategoriesByCategoryId(Long id);

    @Query("select new adilet.dto.response.SubcategoryResponse( s.id, s.name, s.category.name) from Subcategory s")
    List<SubcategoryResponse> getAllSb();

    @Query("select s.name from Subcategory s join s.category c where c.id = s.id group by s.name ")
    List<String> subCatName();


}
