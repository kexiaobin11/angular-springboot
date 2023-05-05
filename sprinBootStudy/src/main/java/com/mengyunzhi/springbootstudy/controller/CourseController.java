package com.mengyunzhi.springbootstudy.controller;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Course save(@RequestBody Course course) {
        return this.courseService.save(course);
    }
    @GetMapping("existsByName")
    public boolean existsByName(@RequestParam String name) {
        return this.courseService.existsByName(name);
    }

    @GetMapping
    public Page<Course> findAll(@RequestParam(required = false) String name,
                                @RequestParam(required = false) Long klassId,
                                @RequestParam(required = false) Long teacherId,
                                @RequestParam int page,
                                @RequestParam int size) {
        return this.courseService.findAll(name, klassId, teacherId, PageRequest.of(page, size));
    }



}
