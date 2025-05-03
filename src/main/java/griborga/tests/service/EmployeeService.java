package griborga.tests.service;

import griborga.tests.entity.Employee;
import griborga.tests.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeService {
    private static final String API_URL = "https://dummy.restapiexample.com/api/v1";
    private final EmployeeRepository repository;
    private final RestTemplate restTemplate;

    @PostConstruct
    public void cacheEmployeesOnStartup() {
        syncAllEmployees();
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee fetchEmployeeById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with provided ID(%d) was not found", id)));
    }

    public List<Employee> syncAllEmployees() {
        Employee[] employees = restTemplate.getForObject(API_URL + "/employees", Employee[].class);
        List<Employee> entityList = Arrays.stream(employees).filter(Objects::nonNull).collect(Collectors.toList());
        return repository.saveAll(entityList);
    }

    @CachePut(value = "employees", key = "#employee.id")
    public Employee updateEmployee(Employee employee) {
        restTemplate.put(API_URL + "/update/" + employee.getId(), employee);
        return repository.save(employee);
    }

    @CacheEvict(value = "employees", key = "#id")
    public void deleteEmployee(Long id) {
        restTemplate.delete(API_URL + "/delete/" + id);
        repository.deleteById(id);
    }
}
