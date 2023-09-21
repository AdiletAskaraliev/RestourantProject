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

    @Query(nativeQuery = true,
            value = "SELECT c.id AS category_id, c.name AS category_name, " +
                    "s.id AS subcategory_id, s.name AS subcategory_name " +
                    "FROM Subcategory s " +
                    "JOIN Category c on c.id =  s.category.id" +
                    "group by c.id, c.name, s.id, s.name")
    List<SubcategoryGroupResponse> getGroupedSubcategoriesByCategory();








}
