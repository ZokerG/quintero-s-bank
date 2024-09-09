package co.com.quinteropo.core.quinteropobank.common.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCategoryTransferRequest {
        private String name;
        private String description;
}
