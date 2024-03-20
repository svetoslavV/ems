package com.huskycare.ems.service;

import com.huskycare.ems.model.Department;
import com.huskycare.ems.model.Employee;
import com.huskycare.ems.error.EmployeeNotFoundException;

import java.math.BigDecimal;
import java.util.UUID;

public interface EmployeeService {
    /**
     * Create new employee. Return newly created instance of {@link Employee} object
     * @param name text field, required, maximum length 255 symbols
     * @param department employee department choose one of {@link Department} entries
     * @param salary employee salary, floating point
     * @return {@link Employee} instance
     */
    Employee createEmployee(String name, Department department, BigDecimal salary);

    /**
     * Update an employee by id with provided values, if employee not found {@link EmployeeNotFoundException} error is thrown
     * @param id uuid of the employee
     * @param name the new name of the employee
     * @param department the new department
     * @param salary the new salary
     */
    void updateEmployee(UUID id, String name, Department department, BigDecimal salary);

    /**
     * Retrieve an employee by id, if employee not found {@link EmployeeNotFoundException} error is thrown
     * @param id uuid of the employee
     * @return {@link Employee} instance
     */
    Employee getEmployee(UUID id);

    /**
     * Delete un employee, if not found no error is thrown
     * @param id uuid of the employee
     */
    void deleteEmployee(UUID id);

    /**
     * Calculate the bonus of the employee based on his department and salary
     * @param employee
     * @return the bonus rounded with precision of 2 signs after the whole number
     * for example 234.333 -> 234.33 and 234.336 -> 234.34
     */
    BigDecimal calculateBonus(Employee employee);

}
