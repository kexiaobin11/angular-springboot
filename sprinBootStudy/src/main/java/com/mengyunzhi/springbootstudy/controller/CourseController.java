package com.mengyunzhi.springbootstudy.controller;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.service.CourseService;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Course")
public class CourseController {
    @Autowired
    CourseService courseService;
    private final static Logger logger = LoggerFactory.getLogger(CourseController.class);
    @GetMapping("{id}")
    public Course getById(@PathVariable Long id) {
        return this.courseService.findById(id);
    }

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
    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        this.courseService.deleteById(id);
    }

    @PutMapping("{id}")
    public Course update(@PathVariable Long id, @RequestBody Course course) {
       return this.courseService.update(id, course);
    }
    @DeleteMapping("/batch-delete")
    public void bathDelete(@RequestParam List<Long> ids){
        this.courseService.deleteByIdIn(ids);
    }

}