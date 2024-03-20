package com.huskycare.ems.storage;

import com.huskycare.ems.model.Department;
import com.huskycare.ems.model.Employee;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class EmployeeStorageInMemoryImpl implements EmployeeStorage {
    private final Map<UUID, Employee> employeeStore = new ConcurrentHashMap<>();
    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock readLock = rwLock.readLock();
    private final Lock writeLock = rwLock.writeLock();

    public Optional<Employee> getEmployee(UUID id) {
        readLock.lock();

        try {
            return Optional.ofNullable(employeeStore.get(id));
        } finally {
            readLock.unlock();
        }
    }

    public Employee addEmployee(Employee employee) {
        writeLock.lock();

        try {
            employeeStore.put(employee.getId(), employee);
        } finally {
            writeLock.unlock();
        }

        return employee;
    }

    public void updateEmployee(UUID id, String name, Department department, BigDecimal salary) {
        writeLock.lock();

        try {
            Employee employee = employeeStore.get(id);

            if (name != null) {
                employee.setName(name);
            }

            if (department != null) {
                employee.setDepartment(department);
            }

            if (salary != null) {
                employee.setSalary(salary);
            }

            employeeStore.replace(id, employee);
        } finally {
            writeLock.unlock();
        }
    }

    public void deleteEmployee(UUID id) {
        writeLock.lock();

        try {
            employeeStore.remove(id);
        } finally {
            writeLock.unlock();
        }

    }
}
