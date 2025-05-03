package griborga.tests.service;

import griborga.tests.entity.Employee;
import griborga.tests.models.response.EmployeeApiResponse;
import griborga.tests.repository.EmployeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class EmployeeService {


    private static final String API_URL = "https://dummy.restapiexample.com/api/v1";
    private final EmployeeRepository repository;
    private final CacheManager cacheManager;
    private final RestTemplate restTemplate;


    @PostConstruct
    public void cacheEmployeesOnStartup() {
        syncAllEmployees();
    }

    @Cacheable(value = "employees", key = "#id")
    public Employee fetchEmployeeById(Long id) {
        System.out.println("Fetching employee with id: " + id + " - This should only appear if it's not cached"); // Just to make suure caching is working as expected
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Student with provided ID(%d) was not found", id)));
    }

    public List<Employee> syncAllEmployees() {
        EmployeeApiResponse response = restTemplate.getForObject(API_URL + "/employees", EmployeeApiResponse.class);
        List<Employee> entityList = response == null || response.getData() == null ? Collections.emptyList() : response.getData();

        List<Employee> savedEmployees = repository.saveAll(entityList);

        Cache cache = cacheManager.getCache("employees");
        if (cache != null) {
            for (Employee employee : savedEmployees) {
                cache.put(employee.getId(), employee);
            }
        }

        return savedEmployees;
    }

    @CachePut(value = "employees", key = "#employee.id")
    public Employee createEmployee(Employee employee) {
        restTemplate.postForObject(API_URL + "/create", employee, Employee.class);
        return repository.save(employee);
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
