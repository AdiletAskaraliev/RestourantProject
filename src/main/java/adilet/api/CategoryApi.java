package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.CategoryRequest;
import adilet.dto.response.CategoryResponse;
import adilet.dto.response.PaginationCategory;
import adilet.entity.Category;
import adilet.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryService categoryService;

    @GetMapping
    public PaginationCategory getAll(@RequestParam int currentPage,
                                     @RequestParam int size){
        return categoryService.findAll(currentPage, size);
    }

    @PostMapping
    public CategoryResponse save(@RequestBody CategoryRequest categoryRequest){
        return categoryService.save(categoryRequest);
    }

    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id){
        return categoryService.findById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody CategoryRequest categoryRequest){
        return categoryService.update(id, categoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return categoryService.delete(id);
    }
}
