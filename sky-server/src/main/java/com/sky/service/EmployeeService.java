package com.sky.service;

import com.sky.dto.EmployeeLoginDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

import java.util.List;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    PageResult getPage(Integer page, Integer size);

    void save(Employee employee);

    void update(Employee employee);

    Employee getById(Integer id);

    void status(Integer status, Long id);
}
