package com.huskycare.ems.storage;

import com.huskycare.ems.model.Department;
import com.huskycare.ems.model.Employee;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeStorage {
    Optional<Employee> getEmployee(UUID id);
    Employee addEmployee(Employee employee);
    void updateEmployee(UUID id, String name, Department department, BigDecimal salary);
    void deleteEmployee(UUID id);
}
