package co.com.quinteropo.core.quinteropobank.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashResponse {
    private String name;
    private List<SeriesResponse> series;
}
