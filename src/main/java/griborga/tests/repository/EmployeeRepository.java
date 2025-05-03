package griborga.tests.repository;

import griborga.tests.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findById(Long key);

    void deleteById(Long id);
}