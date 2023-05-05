package com.mengyunzhi.springbootstudy.repository;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.repository.specs.CourseSpecs;
import com.mengyunzhi.springbootstudy.service.CourseService;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CourseRepositoryTest {
    @Autowired
    CourseRepository courseRepository;

    @MockBean
    CourseService courseService;
    @MockBean
    CourseSpecs courseSpecs;

    @MockBean
    TeacherRepository teacherRepository;

    @MockBean
    KlassRepository klassRepository;

    @Test
    public void existByName() {
        // 生成随机字符串的课程名
        // 调用existsByName方法，断言返回false
        // 新建课程，课程名用上面生成的随机字符串，保存课程
        // 再次调用existsByName方法，断言返回true
        String name = RandomString.make(10);
        Assert.assertFalse(this.courseRepository.existsByName(name));
        // 新建课程，课程名用上面生成的随机字符串，保存课程
        Course course = new Course();
        course.setName(name);
        this.courseRepository.save(course);
        Assert.assertTrue(this.courseRepository.existsByName(name));
    }
    @Test
    public void existsByName() {
        String name = RandomString.make(10);
        courseRepository = Mockito.mock(CourseRepository.class);
        Mockito.when(this.courseRepository.existsByName(name)).thenReturn(false);
        boolean result = this.courseService.existsByName(name);
        Assert.assertFalse(result);
    }

    @Test
    @Transactional(readOnly = true)
    public void findAll1() {
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        Page<Course> courses = courseRepository.findAll("计算机网络", teacher,PageRequest.of(0, 2));
      List<Course> courseList =  courses.getContent();
      for (Course course : courseList) {
          System.out.println(course);
      }
    }

}