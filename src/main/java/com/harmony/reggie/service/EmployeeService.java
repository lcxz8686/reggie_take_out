package com.harmony.reggie.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Employee;

import javax.servlet.http.HttpServletRequest;

public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request, Employee employee);

    R<String> logout(HttpServletRequest request);

    R<String> save(HttpServletRequest request, Employee employee);

    R<Page> page(int page, int pageSize, String name);

    R<String> updateInfo(HttpServletRequest request, Employee employee);
}
