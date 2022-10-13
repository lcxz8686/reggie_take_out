package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Employee;
import com.harmony.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 员工登入
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // @RequestBody : 用来接收前端传递给后端的json字符串中的数据的
        return employeeService.login(request,employee);
    }

    /**
     * 员工退出
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        return employeeService.logout(request);
    }

    /**
     * 新增员工
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> save(HttpServletRequest request, @RequestBody Employee employee) {
        return employeeService.save(request,employee);
    }

    /**
     * 员工信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {
        log.info("page = {},pageSize = {},name = {}", page, pageSize, name);
        return employeeService.page(page, pageSize, name);
    }

    /**
     * 根据 ID 来修改员工信息
     * @param employee
     * @return
     */
    @PutMapping
    public R<String> updateInfo(HttpServletRequest request,@RequestBody Employee employee) {
        return employeeService.updateInfo(request, employee);
    }

    /**
     * 查询员工信息
     * @param id
     * @return
     * @PathVariable id在请求路径里面
     */
    @GetMapping("/{id}")
    public R<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("没有查询出来！");
    }
}
