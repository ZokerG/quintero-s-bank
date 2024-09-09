package co.com.quinteropo.core.quinteropobank.common.mapper;

import co.com.quinteropo.core.quinteropobank.domain.model.ClientRecord;
import java.time.LocalDate;


public class ClientMapper {

    private ClientMapper() {
    }

    public static ClientRecord mapToCreateClientRecord(String name, String lastName, String phone, String email, String documentType, String document, String province, String city, String password) {
        ClientRecord clientRecord = new ClientRecord();
        clientRecord.setName(name);
        clientRecord.setLastName(lastName);
        clientRecord.setPhone(phone);
        clientRecord.setEmail(email);
        clientRecord.setDocumentType(documentType);
        clientRecord.setDocument(document);
        clientRecord.setProvince(province);
        clientRecord.setCity(city);
        clientRecord.setPassword(password);
        clientRecord.setCreatAt(LocalDate.now());
        return clientRecord;
    }
}
