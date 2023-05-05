package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    Course save(Course course);
    Boolean existsByName(String name);

    Page<Course> findAll(String name, Long klassId, Long teacherId, @NotNull Pageable pageable);
}
