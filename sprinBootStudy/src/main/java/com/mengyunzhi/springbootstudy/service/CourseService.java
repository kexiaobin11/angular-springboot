package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.sun.istack.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseService {
    Course save(Course course);
    Boolean existsByName(String name);

    Page<Course> findAll(String name, Long klassId, Long teacherId, @NotNull Pageable pageable);

    void deleteById(Long id);

    Course findById(Long id);
    Course update(Long id, Course course);
    @Transactional
    void deleteByIdIn( List<Long> ids);
}
