package com.mengyunzhi.springbootstudy.repository.specs;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.repository.CourseRepository;
import com.mengyunzhi.springbootstudy.repository.KlassRepository;
import com.mengyunzhi.springbootstudy.repository.StudentRepository;
import com.mengyunzhi.springbootstudy.repository.TeacherRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
class CourseSpecsTest {
    private static final Logger logger = LoggerFactory.getLogger(CourseSpecsTest.class);
    @Autowired
    private KlassRepository klassRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private CourseRepository courseRepository;

    private Course course;
    @Before
    public void before() {
        Klass klass = new Klass();
        klass.setName("testKlass");
        this.klassRepository.save(klass);
        Klass klass1 = new Klass();
        klass.setName("testKlass1");
        this.klassRepository.save(klass1);
        List<Klass> klasses = new ArrayList<Klass>();
        klasses.add(klass);
        klasses.add(klass1);
        Teacher teacher = new Teacher();
        teacher.setUsername("testUsername");
        teacher.setName("张三");
        teacher.setUsername("zhangsan");
        this.teacherRepository.save(teacher);
        this.course.setName("testCourse");
        this.course.setTeacher(teacher);
        this.course.setKlasses(klasses);
        this.courseRepository.save(course);
    }
    @Test
    public void belongKlass() {
    }

}