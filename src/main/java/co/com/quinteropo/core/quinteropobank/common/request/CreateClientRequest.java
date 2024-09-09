package co.com.quinteropo.core.quinteropobank.common.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientRequest {
    private String name;
    private String lastName;
    private String phone;
    private String email;
    private String documentType;
    private String document;
    private String province;
    private String city;
    private String password;
    private String confirmPassword;
}
