package com.mengyunzhi.springbootstudy.service;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        return this.courseRepository.findAll(name,klassId,teacherId, pageable);
    }

    @Override
    public void deleteById(Long id) {
        this.courseRepository.deleteById(id);
    }

    @Override
    public Course findById(Long id) {
        return this.courseRepository.findById(id).get();
    }

    @Override
    public Course update(Long id, Course course) {
        System.out.println(course);
      Course oldCourse = this.courseRepository.findById(id).get();
      oldCourse.setTeacher(course.getTeacher());
      oldCourse.setName(course.getName());
      oldCourse.setKlasses(course.getKlasses());
      return this.courseRepository.save(oldCourse);
    }
    @Transactional
    @Override
    public void deleteByIdIn(List<Long> ids) {
        this.courseRepository.deleteByIdIn(ids);
    }


}
