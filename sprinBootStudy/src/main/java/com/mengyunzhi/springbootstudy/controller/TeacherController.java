package com.mengyunzhi.springbootstudy.controller;

import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.service.TeacherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("Teacher")
public class TeacherController {
    private final static Logger logger = LoggerFactory.getLogger(TeacherController.class.getName());

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    TeacherService teacherService;

    @DeleteMapping("{id}")
    @CrossOrigin("*")
    public void delete(@PathVariable Long id) {
        String sql = String.format( "delete from `teacher` where id = %s", id);
        this.jdbcTemplate.update(sql);
    }

    @GetMapping
    public List<Teacher> getAll() {
        return this.teacherService.getAll();
    }

    /**
     *根据Id 请求值
     *
     * @param id 教师ID
     * @return
     */
    @GetMapping("{id}")
    public Teacher getById(@PathVariable Long id) {
        return this.teacherService.getById(id);
    }

    /*
    * 添加数据时候设置默认密码
    * */
    @PostMapping
    public void save(@RequestBody Teacher teacher) {
        teacher.setPassword("123456");
        this.teacherService.save(teacher);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody Teacher newTeacher) {
        this.teacherService.update(id, newTeacher);
    }

    /*
    * 请求登陆验证
    *
    * @param teacher
    * */
    @PostMapping("login")
    public boolean login(@RequestBody Teacher teacher) {
        return this.teacherService.login(teacher.getUsername(), teacher.getPassword());
    }

    /*
    * 实现注销功能
    * */
    @GetMapping("logout")
    @CrossOrigin("*")
    public void login() {
        this.teacherService.logout();
    }

    /*
    * 判断我是谁
    * 如果我存在teacher
    * */
    @GetMapping("me")
    public Teacher me() {
        return this.teacherService.me();
    }
}