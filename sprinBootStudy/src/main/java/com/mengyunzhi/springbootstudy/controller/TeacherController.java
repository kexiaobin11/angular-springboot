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
        List<Teacher> teachers = new ArrayList<>();
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Teacher teacher = new Teacher();
                teacher.setId(resultSet.getLong("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setSex(resultSet.getBoolean("sex"));
                teacher.setUsername(resultSet.getString("username"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setCreateTime(resultSet.getLong("create_time"));
                teacher.setUpdateTime(resultSet.getLong("update_time"));
                teachers.add(teacher);
            }
        };

        String query = "select id, name, sex, username, email, create_time, update_time from teacher";

        jdbcTemplate.query(query, rowCallbackHandler);
        return teachers;
    }

    /**
     *根据Id 请求值
     *
     * @param id 教师ID
     * @return
     */
    @GetMapping("{id}")
    public Teacher getById(@PathVariable Long id) {
        Teacher teacher = new Teacher();

        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                teacher.setId(resultSet.getLong("id"));
                teacher.setName(resultSet.getString("name"));
                teacher.setSex(resultSet.getBoolean("sex"));
                teacher.setUsername(resultSet.getString("username"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setCreateTime(resultSet.getLong("create_time"));
                teacher.setUpdateTime(resultSet.getLong("update_time"));
            }
        };

        String query = String.format("select id, name, sex, username, email, create_time, update_time from teacher where id = %d", id);

        jdbcTemplate.query(query, rowCallbackHandler);

        return teacher;
    }

    @PostMapping
    public void save(@RequestBody Teacher teacher) {
        String sql = String.format(
                "insert into `teacher` (`name`, `username`, `email`, `sex`) values ('%s', '%s', '%s', %s)",
                teacher.getName(), teacher.getUsername(), teacher.getEmail(), teacher.getSex().toString()
        );
        logger.info(sql);
        jdbcTemplate.execute(sql);
    }

    @PutMapping("{id}")
    public void update(@PathVariable Long id, @RequestBody Teacher newTeacher) {
        String sql = String.format(
                "update `teacher` set `name` = '%s'  , `username` = '%s' , `email` = '%s' , `sex` = %s where `id` = %s",
                newTeacher.getName(), newTeacher.getUsername(), newTeacher.getEmail(), newTeacher.getSex().toString(), id
        );
        this.jdbcTemplate.update(sql);
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