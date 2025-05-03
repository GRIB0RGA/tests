package griborga.tests.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    private Long id;

    @JsonProperty("employee_name")
    private String name;

    @JsonProperty("employee_salary")
    private Double salary;

    @JsonProperty("employee_age")
    private Integer age;
}
