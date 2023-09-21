package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.SubcategoryRequest;
import adilet.dto.response.SubcategoryGroupResponse;
import adilet.dto.response.SubcategoryResponse;
import adilet.entity.Category;
import adilet.entity.Subcategory;
import adilet.exception.NotFoundException;
import adilet.repository.CategoryRepository;
import adilet.repository.SubcategoryRepository;
import adilet.service.SubcategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SubcategoryServiceImpl implements SubcategoryService {

    private final SubcategoryRepository subcategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }

    @Override
    public SubcategoryResponse save(Long categoryId, SubcategoryRequest subcategoryRequest) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> {
                    log.error("Category with id: " + categoryId + " not found");
                    return new NotFoundException("Category with id: " + categoryId + " not found");
                });

        Subcategory subcategory = new Subcategory();
        subcategory.setCategory(category);

        subcategory.setName(subcategoryRequest.getName());

        subcategoryRepository.save(subcategory);

        log.info("Successfully saved");
        return new SubcategoryResponse(
                subcategory.getId(),
                subcategory.getName()
        );
    }

    @Override
    public SubcategoryResponse findById(Long id) {
        return subcategoryRepository.findSubcategoriesById(id)
                .orElseThrow(() -> {
                    log.error("Subcategory with id: " + id + " not found");
                    return new NotFoundException("Subcategory with id: " + id + " not found");
                });
    }

    @Override
    public SimpleResponse update(Long id, SubcategoryRequest subcategoryRequest) {
        Subcategory subcategory = subcategoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Subcategory with id: " + id + " not found");
                    return new NotFoundException("Subcategory with id: " + id + " not found");
                });

        subcategory.setName(subcategoryRequest.getName());
        subcategoryRepository.save(subcategory);
        log.info("Subcategory successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        subcategoryRepository.deleteById(id);
        log.info("Subcategory successfully deleted");

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }

    @Override
    public List<SubcategoryResponse> getSubcategoryByCategoryId(Long categoryId) {
        return subcategoryRepository.findSubcategoriesByCategoryId(categoryId);
    }

    @Override
    public List<SubcategoryGroupResponse> getGroupSubcategoriesByCategory() {
     return subcategoryRepository.getGroupedSubcategoriesByCategory();
    }

}


