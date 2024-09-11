package co.com.quinteropo.core.quinteropobank.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeriesResponse {
    private double value;
    private String name;
}
