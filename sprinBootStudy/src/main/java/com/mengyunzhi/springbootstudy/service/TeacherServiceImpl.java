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
public class TeacherServiceImpl implements TeacherSerivce {
    private final Logger logger = LoggerFactory.getLogger(TeacherServiceImpl.class);
    private final HttpServletRequest request;
    private TeacherRepository teacherRepository;
    private HashMap<String, Long> authTokenTeacherIdHashMap = new HashMap<>();

    @Autowired
    public TeacherServiceImpl(TeacherRepository teacherRepository, HttpServletRequest request) {
        this.teacherRepository = teacherRepository;
        this.request = request;
    }

    @Override
    public boolean login(String username, String password) {
        Teacher teacher = this.teacherRepository.findByUsername(username);
        logger.info("获取到的auth-token为" + this.request.getHeader(TokenFilter.TOKEN_KEY));
        if (!this.validatePassword(teacher, password)) {
            // 认证不成功直接返回
            return false;
        }
        this.authTokenTeacherIdHashMap.put(this.request.getHeader(TokenFilter.TOKEN_KEY), teacher.getId());
        return true;
    }

    @Override
    public boolean validatePassword(Teacher teacher, String password) {
        if (teacher == null || teacher.getPassword() == null || password == null) {
            return false;
        }
        return teacher.getPassword().equals(password);
    }

    @Override
    public void logout() {
        String authToken = this.request.getHeader(TokenFilter.TOKEN_KEY);
        logger.info("获取到的auth-token为" + this.request.getHeader(TokenFilter.TOKEN_KEY));
        this.authTokenTeacherIdHashMap.remove(authToken);
    }

    @Override
    public Teacher me() {
        // 获取authToken
        String authToken = this.request.getHeader(TokenFilter.TOKEN_KEY);
        // 获取authToken映射的teacherId
        Long teacherId = this.authTokenTeacherIdHashMap.get(authToken);
        logger.info("映射teacherId的值" + teacherId);
        if (teacherId == null) {
            return null;
        }
        Optional<Teacher> teacherOptional = this.teacherRepository.findById(teacherId);
        return teacherOptional.get();
    }
}
