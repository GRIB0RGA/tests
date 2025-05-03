package griborga.tests.models.response;

import griborga.tests.entity.Employee;
import lombok.Data;
import java.util.List;

@Data
public class EmployeeApiResponse {
    private String status;
    private List<Employee> data;
}
