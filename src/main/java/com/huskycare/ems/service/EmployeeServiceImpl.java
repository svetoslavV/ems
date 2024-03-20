package com.huskycare.ems.service;

import com.huskycare.ems.error.EmployeeNotFoundException;
import com.huskycare.ems.model.Department;
import com.huskycare.ems.model.Employee;
import com.huskycare.ems.storage.EmployeeStorage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeStorage employeeStorage;
    public EmployeeServiceImpl(EmployeeStorage employeeStorage) {
        this.employeeStorage = employeeStorage;
    }
    @Override
    public Employee createEmployee(String name, Department department, BigDecimal salary) {
        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName(name);
        employee.setDepartment(department);
        employee.setSalary(salary);

        return employeeStorage.addEmployee(employee);
    }

    @Override
    public Employee getEmployee(UUID id) {
        return employeeStorage.getEmployee(id).orElseThrow(EmployeeNotFoundException::new);
    }

    @Override
    public void updateEmployee(UUID id, String name, Department department, BigDecimal salary) {
        employeeStorage.updateEmployee(id, name, department, salary);
    }

    @Override
    public void deleteEmployee(UUID id) {
        employeeStorage.deleteEmployee(id);
    }

    public BigDecimal calculateBonus(Employee employee) {
        BigDecimal percentage = new BigDecimal(employee.getDepartment().getBonusPercentage())
                .divide(new BigDecimal(100));
        return employee.getSalary()
                .multiply(percentage).setScale(2, RoundingMode.HALF_UP);
    }
}
