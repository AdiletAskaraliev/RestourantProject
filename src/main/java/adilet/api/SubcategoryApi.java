package adilet.api;

import adilet.dto.SimpleResponse;
import adilet.dto.request.SubcategoryRequest;
import adilet.dto.response.SubcategoryGroupResponse;
import adilet.dto.response.SubcategoryResponse;
import adilet.entity.Subcategory;
import adilet.service.SubcategoryService;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/subcategories")
@RequiredArgsConstructor
public class SubcategoryApi {
    private final SubcategoryService subcategoryService;

    @GetMapping
    public List<Subcategory> getAll(){
        return subcategoryService.findAll();
    }

    @PostMapping("/{categoryId}")
    public SubcategoryResponse save(@RequestBody SubcategoryRequest subcategoryRequest,
                                    @PathVariable Long categoryId){
        return subcategoryService.save(categoryId, subcategoryRequest);
    }

    @GetMapping("/{id}")
    public SubcategoryResponse findById(@PathVariable Long id){
        return subcategoryService.findById(id);
    }

    @PutMapping("/{id}")
    public SimpleResponse update(@PathVariable Long id,
                                 @RequestBody SubcategoryRequest subcategoryRequest){
        return subcategoryService.update(id, subcategoryRequest);
    }

    @DeleteMapping("/{id}")
    public SimpleResponse delete(@PathVariable Long id){
        return subcategoryService.delete(id);
    }

    @GetMapping("/getSubcategory/{categoryId}")
    public List<SubcategoryResponse> getSubcategoryByCategoryId(@PathVariable Long categoryId){
        return subcategoryService.getSubcategoryByCategoryId(categoryId);
    }

    @PermitAll
    @GetMapping("/grouping")
    public Map<String,List<SubcategoryResponse>> filter(){
        return subcategoryService.filterByCategory();
    }
}
