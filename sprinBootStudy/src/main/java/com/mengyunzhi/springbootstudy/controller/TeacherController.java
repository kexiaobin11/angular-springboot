package com.mengyunzhi.springbootstudy.controller;

import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.service.TeacherSerivce;
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
    TeacherSerivce teacherService;

    @DeleteMapping("{id}")
    @CrossOrigin("*")
    public void delete(@PathVariable Long id) {
        String sql = String.format( "delete from `teacher` where id = %s", id);
        this.jdbcTemplate.update(sql);
    }

    @GetMapping
    @CrossOrigin("*")
    public List<Teacher> getAll() {
        /*初始化不固定大小的数组*/
        List<Teacher> teachers = new ArrayList<>();

        /* 定义实现了RowCallbackHandler接口的对象*/
        RowCallbackHandler rowCallbackHandler = new RowCallbackHandler() {
            /**
             * 该方法用于执行jdbcTemplate.query后的回调，每行数据回调1次。比如Teacher表中有两行数据，则回调此方法两次。
             *
             * @param resultSet 查询结果，每次一行
             * @throws SQLException 查询出错时，将抛出此异常，暂时不处理。
             */
            @Override
            public void processRow(ResultSet resultSet) throws SQLException {
                Teacher teacher = new Teacher();
                /*获取字段id，并转换为Long类型返回*/
                teacher.setId(resultSet.getLong("id"));
                /*获取字段name，并转换为String类型返回*/
                teacher.setName(resultSet.getString("name"));
                /*获取字段sex，并转换为布尔类型返回*/
                teacher.setSex(resultSet.getBoolean("sex"));
                teacher.setUsername(resultSet.getString("username"));
                teacher.setEmail(resultSet.getString("email"));
                teacher.setCreateTime(resultSet.getLong("create_time"));
                teacher.setUpdateTime(resultSet.getLong("update_time"));

                /*将得到的teacher添加到要返回的数组中*/
                teachers.add(teacher);
            }
        };

        /*定义查询字符串*/
        String query = "select id, name, sex, username, email, create_time, update_time from teacher";

        /*使用query进行查询，并把查询的结果通过调用rowCallbackHandler.processRow()方法传递给rowCallbackHandler对象*/
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
    @CrossOrigin("*")
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

    /**
     * 新增教师
     * 1. 获取前台传入的教师对象
     * 2. 拼接插入sql语句
     * 3. 执行sql语句。
     *
     * @param teacher 教师
     */
    @PostMapping
    @CrossOrigin("*")
    public void save(@RequestBody Teacher teacher) {
        String sql = String.format(
                "insert into `teacher` (`name`, `username`, `email`, `sex`) values ('%s', '%s', '%s', %s)",
                teacher.getName(), teacher.getUsername(), teacher.getEmail(), teacher.getSex().toString()
        );
        logger.info(sql);
        jdbcTemplate.execute(sql);
    }

    /**
     * 使用传入的数据更新某个教师的数据
     *
     * @param id 教师ID
     * @param newTeacher 更新教师
     */
    @PutMapping("{id}")
    @CrossOrigin("*")
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