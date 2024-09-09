package co.com.quinteropo.core.quinteropobank.core.service;


import co.com.quinteropo.core.quinteropobank.common.mapper.CategoryTransferMapper;
import co.com.quinteropo.core.quinteropobank.common.request.CreateCategoryTransferRequest;
import co.com.quinteropo.core.quinteropobank.domain.model.CategoryTransferRecord;
import co.com.quinteropo.core.quinteropobank.domain.repository.CategoryTransferRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CategoryTransferService {


    private final CategoryTransferRepository categoryTransferRepository;

    public CategoryTransferService(CategoryTransferRepository categoryTransferRepository) {
        this.categoryTransferRepository = categoryTransferRepository;
    }

    public void deleteCategoryTransfer(long id) {
        log.info("Inside CategoryTransferService::deleteCategoryTransfer id: {}", id);
        categoryTransferRepository.deleteById(id);
    }

    public CategoryTransferRecord findById(long id) {
        return categoryTransferRepository.findById(id).orElseThrow(() -> new RuntimeException("CategoryTransfer not found"));
    }

    public CategoryTransferRecord findByCategoryName(String categoryName) {
        return categoryTransferRepository.findByName(categoryName).orElseThrow(() -> new RuntimeException("CategoryTransfer not found"));
    }

    public CategoryTransferRecord createCategoryTransfer(CreateCategoryTransferRequest createCategoryTransferRequest) {
        log.info("Inside CategoryTransferService::createCategoryTransfer createCategoryTransferRequest: {}", createCategoryTransferRequest);
        if (categoryTransferRepository.existsByName(createCategoryTransferRequest.getName())) {
            throw new RuntimeException("CategoryTransfer already exists");
        }
        return categoryTransferRepository.save(CategoryTransferMapper.toCreateCategoryTransferRecord(createCategoryTransferRequest));
    }
}
