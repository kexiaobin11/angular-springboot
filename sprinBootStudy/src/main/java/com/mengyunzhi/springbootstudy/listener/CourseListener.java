package com.mengyunzhi.springbootstudy.listener;

import com.mengyunzhi.springbootstudy.entity.Course;
import org.springframework.dao.DataIntegrityViolationException;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

public class CourseListener {
    @PrePersist
    @PreUpdate
    public void preUpdate(Course course) {
        if (course.getName() == null || course.getName().length() < 2) {
            throw new DataIntegrityViolationException("课程名称长度最小为2位");
        }
    }
}
