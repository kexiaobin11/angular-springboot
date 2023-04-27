package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.filter.TokenFilter;
import com.mengyunzhi.springbootstudy.repository.TeacherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Optional;

@Service
public class TeacherServiceImpl implements TeacherService {
    private final static Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final HttpServletRequest request;
    private TeacherRepository teacherRepository;

    /**
     * auth-token与teacherId的映射
     */
    private HashMap<String, Long> authTokenTeacherIdHashMap = new HashMap<>();

    /*
    * 进行依赖注入，和AutoWired 相同，相对比后者更加安全
    * */
    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, HttpServletRequest request) {
        this.teacherRepository = teacherRepository;
        this.request = request;
    }

    /*
    * 进行登陆验证
    * @param username
    * @param password
    * 验证是否存在这个用户，如果存在返回true，如果不存在返回false
    * 取得token的值于登陆的id进行绑定
    * 总结登陆成功之后跟id 跟 token判断
    * */
    @Override
    public boolean login(String username, String password) {
        Teacher teacher = this.teacherRepository.findByUsername(username);
        if (!this.validatePassword(teacher, password)) {
            return false;
        }
        logger.info("获取到的auth-token为" + this.request.getHeader(TokenFilter.TOKEN_KEY));
        this.authTokenTeacherIdHashMap.put(this.request.getHeader(TokenFilter.TOKEN_KEY), teacher.getId());
        return true;
    }

    /**
     * 注销实现
     * 存在在authToken的值设置为空
     * 移除当前的问题
     */
    @Override
    public void logout() {
        String authToken = this.request.getHeader(TokenFilter.TOKEN_KEY);
        logger.info("获取到的auth-token为" + this.request.getHeader(TokenFilter.TOKEN_KEY));
        this.authTokenTeacherIdHashMap.remove(authToken);
    }

    /**
     * 获得个人信息
     * 通过使用hashMap 进行K-v 判断，根据前台传入过来的token值，取到当前的用户登陆的Id
     */
    @Override
    public Teacher me() {
        // 获取authToken
        String authToken = this.request.getHeader(TokenFilter.TOKEN_KEY);
        // 获取authToken映射的teacherId
        Long teacherId = this.authTokenTeacherIdHashMap.get(authToken);
        if (teacherId == null) {
            return null;
        }

        // 如获取到teacherId，则由数据库中获取teacher并返回
        Optional<Teacher> teacherOptional = this.teacherRepository.findById(teacherId);
        return teacherOptional.get();
    }

    @Override
    public boolean validatePassword(Teacher teacher, String password) {
        if (teacher == null || teacher.getPassword() == null || password == null) {
            return false;
        }

        return teacher.getPassword().equals(password);
    }

    @Override
    public boolean isLogin(String authToken) {
        Long teacherId = this.authTokenTeacherIdHashMap.get(authToken);
        return teacherId != null;
    }
}
