package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.common.request.CreateCategoryTransferRequest;
import co.com.quinteropo.core.quinteropobank.domain.model.CategoryTransferRecord;

import java.time.LocalDate;

public class CategoryTransferMapper {

    private CategoryTransferMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static CategoryTransferRecord toCreateCategoryTransferRecord(CreateCategoryTransferRequest request) {
        CategoryTransferRecord categoryTransferRecord = new CategoryTransferRecord();
        categoryTransferRecord.setName(request.getName());
        categoryTransferRecord.setDescription(request.getDescription());
        categoryTransferRecord.setCreatAt(LocalDate.now());
        return categoryTransferRecord;
    }
}
