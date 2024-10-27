package project.kidscompetitions.Repository.interfaces;

import project.kidscompetitions.Domain.Employee;

import java.util.Optional;

public interface IEmployeeRepository extends IRepository<Long, Employee> {
    public Optional<Employee> findOneByUsername(String user);}
