package adilet.service.impl;

import adilet.dto.SimpleResponse;
import adilet.dto.request.CategoryRequest;
import adilet.dto.response.CategoryResponse;
import adilet.dto.response.PaginationCategory;
import adilet.entity.Category;
import adilet.exception.NotFoundException;
import adilet.repository.CategoryRepository;
import adilet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public PaginationCategory findAll(int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage, size);
        Page<CategoryResponse> allPagination = categoryRepository.findAllPagination(pageable);
        return  PaginationCategory.builder()
                .categoryResponses(allPagination.getContent())
                .currentPage(allPagination.getNumber())
                .size(allPagination.getTotalPages())
                .build();
    }


    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);

        log.info("Category successfully saved");
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }

    @Override
    public CategoryResponse findById(Long id) {
        return categoryRepository.findCategoriesById(id)
                .orElseThrow(() -> {
                    log.error("Category with id: " + id + " not found");
                    return new NotFoundException("Category with id: " + id + " not found");
                });
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Category with id: " + id + " not found");
                    return new NotFoundException("Category with id: " + id + " not found");
                });

        category.setName(categoryRequest.getName());

        categoryRepository.save(category);
        log.info("Category successfully updated");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        categoryRepository.deleteById(id);
        log.info("Category successfully deleted");
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted")
                .build();
    }




}
