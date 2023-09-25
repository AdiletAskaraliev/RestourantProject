package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.SubcategoryRequest;
import adilet.dto.response.SubcategoryResponse;
import adilet.entity.Subcategory;

import java.util.List;
import java.util.Map;

public interface SubcategoryService {
    List<Subcategory> findAll();

    SubcategoryResponse save(Long categoryId, SubcategoryRequest subcategoryRequest);

    SubcategoryResponse findById(Long id);

    SimpleResponse update(Long id, SubcategoryRequest subcategoryRequest);

    SimpleResponse delete(Long id);

    List<SubcategoryResponse> getSubcategoryByCategoryId(Long categoryId);

    Map<String, List<SubcategoryResponse>> filterByCategory();

}
