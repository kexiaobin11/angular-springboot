package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Teacher;

/**
 * @author kexiaobin
 */
public interface TeacherSerivce {
    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 成功 true
     */
    boolean login(String username, String password);

    /**
     * 验证密码的有效性
     * @param teacher 教师
     * @param password 密码
     * @return 有效 true
     */
    boolean validatePassword(Teacher teacher, String password);
}
