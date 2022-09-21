package com.harmony.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.harmony.reggie.common.R;
import com.harmony.reggie.entity.Employee;
import com.harmony.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.DigestException;

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

        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        if(emp == null){
            return R.error("登入失败");
        }

        if(!emp.getPassword().equals(password)) {
            return R.error("登入失败");
        }

        if(emp.getStatus() == 0) {
            return R.error("账号已禁用");
        }

        request.getSession().setAttribute("employee",emp.getId());

        return R.success(emp);
    }
}
