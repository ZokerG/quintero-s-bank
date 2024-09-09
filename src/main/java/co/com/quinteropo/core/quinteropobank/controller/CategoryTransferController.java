package co.com.quinteropo.core.quinteropobank.controller;

import co.com.quinteropo.core.quinteropobank.common.request.CreateCategoryTransferRequest;
import co.com.quinteropo.core.quinteropobank.core.service.CategoryTransferService;
import co.com.quinteropo.core.quinteropobank.domain.model.CategoryTransferRecord;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/category-transfer")
public class CategoryTransferController {

    private final CategoryTransferService categoryTransferService;

    public CategoryTransferController(CategoryTransferService categoryTransferService) {
        this.categoryTransferService = categoryTransferService;
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCategoryTransfer(@PathVariable long id) {
        categoryTransferService.deleteCategoryTransfer(id);
    }

    @GetMapping("/find-by-id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryTransferRecord findById(@PathVariable long id) {
        return categoryTransferService.findById(id);
    }

    @GetMapping("/find-by-category-name")
    @ResponseStatus(HttpStatus.OK)
    public CategoryTransferRecord findByCategoryName(@RequestParam String categoryName) {
        return categoryTransferService.findByCategoryName(categoryName);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryTransferRecord createCategoryTransfer(@RequestBody CreateCategoryTransferRequest createCategoryTransferRequest) {
        return categoryTransferService.createCategoryTransfer(createCategoryTransferRequest);
    }
}
