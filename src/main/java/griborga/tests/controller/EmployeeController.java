package griborga.tests.controller;

import griborga.tests.entity.Employee;
import griborga.tests.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return service.fetchEmployeeById(id);
    }

    @PutMapping
    public Employee updateEmployee(@RequestBody Employee employee) {
        return service.updateEmployee(employee);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
    }

    @GetMapping("/sync")
    public List<Employee> syncEmployees() {
        return service.syncAllEmployees();
    }
}
