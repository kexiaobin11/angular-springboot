package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Teacher;

import java.util.List;

/**
 * @author kexiaobin
 */
public interface TeacherService {
    List<Teacher> getAll();

    Teacher getById(Long id);

    void save(Teacher teacher);

    void update(Long id, Teacher teacher);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功 true
     */
    boolean login(String username, String password);

    /**
     * 用户注销
     * 系统可以根据HttpServletRequest获取到header中的令牌令牌
     * 所以注销方法不需要传入任何参数
     */
    void logout();

    /**
     * 我是谁
     * @return 当前登录用户。用户未登录则返回null
     */
    Teacher me();

    /**
     * 验证密码的有效性
     * @param teacher 教师
     * @param password 密码
     * @return 有效 true
     */
    boolean validatePassword(Teacher teacher, String password);

    /*
    *判断用户是否登陆
    * @param authToken 认证
    * */
    boolean isLogin(String authToken);
}
