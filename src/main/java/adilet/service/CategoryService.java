package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.CategoryRequest;
import adilet.dto.response.CategoryResponse;
import adilet.dto.response.PaginationCategory;


public interface CategoryService {


    CategoryResponse save(CategoryRequest categoryRequest);

    CategoryResponse findById(Long id);

    SimpleResponse update(Long id, CategoryRequest categoryRequest);

    SimpleResponse delete(Long id);

    PaginationCategory findAll(int currentPage, int size);
}
