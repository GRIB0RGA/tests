package griborga.tests.models.request;

import griborga.tests.entity.Employee;
import lombok.Data;

@Data
public class EmployeeWrapper {
    private String status;
    private Employee data;
}