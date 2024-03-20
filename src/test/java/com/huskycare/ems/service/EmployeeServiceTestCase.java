package com.huskycare.ems.service;

import com.huskycare.ems.error.EmployeeNotFoundException;
import com.huskycare.ems.model.Department;
import com.huskycare.ems.model.Employee;
import com.huskycare.ems.storage.EmployeeStorageInMemoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class EmployeeServiceTestCase {
    private EmployeeService service;

    @BeforeEach
    void setup() {
        EmployeeStorageInMemoryImpl employeeStorageInMemory = new EmployeeStorageInMemoryImpl();
        service = new EmployeeServiceImpl(employeeStorageInMemory);
    }

    @Test
    void testCreate() {
        Employee employee = service.createEmployee("name", Department.IT, new BigDecimal("1234.567"));

        assertThat(service.getEmployee(employee.getId())).isEqualTo(employee);
    }

    @Test
    void testGetNotFound() {
        assertThrows(EmployeeNotFoundException.class, () -> service.getEmployee(UUID.randomUUID()));
    }

    @Test
    void testDelete() {
        service.deleteEmployee(UUID.randomUUID());
    }

    @Test
    void testUpdate() {
        Employee employee = service.createEmployee("name", Department.ENGINEERING, new BigDecimal("1234.56"));
        Thread thread = new Thread(() -> service.updateEmployee(employee.getId(), "new name", null, null));
//        Thread anotherThread = new Thread(() -> service.updateEmployee(employee.getId(), "another name", Department.IT, null));

        thread.start();
//        anotherThread.start();


        Employee employee1 = service.getEmployee(employee.getId());

        assertThat(employee1.getId()).isEqualTo(employee.getId());
        assertThat(employee1.getName()).isEqualTo("new name");
        assertThat(employee1.getDepartment()).isEqualTo(Department.ENGINEERING);
    }

    @Test
    void testCalculateBonusRoundToLower() {
        UUID id = UUID.randomUUID();
        Employee employee = new EmployeeBuilder()
                .withId(id)
                .withName("some name")
                .withDepartment(Department.ENGINEERING)
                .withSalary(new BigDecimal("1234.33"))
                .build();
        BigDecimal bonus = service.calculateBonus(employee);

        assertThat(bonus).isEqualTo(new BigDecimal("123.43"));
    }

    @Test
    void testCalculateBonusRoundToUpper() {
        UUID id = UUID.randomUUID();
        Employee employee = new EmployeeBuilder()
                .withId(id)
                .withName("some name")
                .withDepartment(Department.ENGINEERING)
                .withSalary(new BigDecimal("1234.66"))
                .build();
        BigDecimal bonus = service.calculateBonus(employee);

        assertThat(bonus).isEqualTo(new BigDecimal("123.47"));
    }

    class EmployeeBuilder {
        private UUID id;
        private String name;
        private Department department;
        private BigDecimal salary;

        public Employee build() {
            return new Employee(this.id, this.name, this.department, this.salary);
        }

        public EmployeeBuilder withId(UUID id) {
            this.id = id;
            return this;
        }

        public EmployeeBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public EmployeeBuilder withDepartment(Department department) {
            this.department = department;
            return this;
        }

        public EmployeeBuilder withSalary(BigDecimal salary) {
            this.salary = salary;
            return this;
        }
    }
}
