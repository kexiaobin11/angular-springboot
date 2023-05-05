package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }
    @Override
    public Course save(Course course) {
        return this.courseRepository.save(course);
    }

    /*
    * @params name
    * */
    @Override
    public Boolean existsByName(String name) {
        return this.courseRepository.existsByName(name);
    }

    @Override
    public Page<Course> findAll(String name, Long klassId, Long teacherId, Pageable pageable) {
        Teacher teacher = new Teacher();
        teacher.setId(teacherId);
        return this.courseRepository.findAll(name, teacher, pageable);
    }
}
