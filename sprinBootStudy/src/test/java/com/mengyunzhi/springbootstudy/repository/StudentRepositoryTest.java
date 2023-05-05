package com.mengyunzhi.springbootstudy.repository;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Student;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.service.StudentService;
import com.mengyunzhi.springbootstudy.service.StudentServiceImpl;
import net.minidev.json.JSONObject;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Optional;
import java.util.Random;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StudentRepositoryTest {
    private static final Logger logger = LoggerFactory.getLogger(StudentRepositoryTest.class);

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    KlassRepository klassRepository;

    @Autowired
    StudentService studentService;
    @Autowired
    MockMvc mockMvc;

    @Test
    public void page() {
        Klass klass = new Klass();
        klass.setName("testKlass");
        this.klassRepository.save(klass);

        for (int i = 0; i < 100; i++) {
            Student student = new Student();
            student.setName(RandomString.make(4));
            student.setSno(RandomString.make(6));
            student.setKlass(klass);
            this.studentRepository.save(student);
        }

        Pageable pageable = PageRequest.of(2, 15);
        Page<Student> studentPage = studentRepository.findAll(pageable);
        return;
    }

    @Test
    public void findAll() {
        /* 初始化2个班级并持久化*/
        Klass klass = new Klass();
        klass.setName("testKlass");
        this.klassRepository.save(klass);

        Klass klass1 = new Klass();
        klass1.setName("testKlass1");
        this.klassRepository.save(klass1);

        Student student = new Student();
        student.setName("testStudentName");
        student.setSno("032282");
        student.setKlass(klass);
        this.studentRepository.save(student);

        /* 初始化2个不同班级的学生并持久化 */
        Student student1 = new Student();
        student1.setName("testStudentName1");
        student1.setSno("032291");
        student1.setKlass(klass1);
        this.studentRepository.save(student1);

        Page studentPage = this.studentRepository.findAll("testStudentName", "032282", klass, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(1);

        studentPage = this.studentRepository.findAll("testStudentName12", "032282", klass, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(0);

        studentPage = this.studentRepository.findAll("testStudentName", "0322821", klass, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(0);

        studentPage = this.studentRepository.findAll("testStudentName", "032282", klass1, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(0);

        studentPage = this.studentRepository.findAll(null, "032282", klass, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(1);

        studentPage = this.studentRepository.findAll(null, null, null, PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(2);

        studentPage = this.studentRepository.findAll(null, null, new Klass(), PageRequest.of(0, 2));
        Assertions.assertThat(studentPage.getTotalElements()).isEqualTo(2);
    }

    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findAllWithPageableIsNull() {
       this.studentRepository.findAll("name", "sno", new Klass(), null);
    }


    @Test
    public void findById() throws Exception {
        Long id = new Random().nextLong();

        // 准备服务层替身被调用后的返回数据
        Student student = new Student();
        student.setId(id);
        student.setSno(new RandomString(6).nextString());
        student.setName(new RandomString(8).nextString());
        student.setKlass(new Klass());
        student.getKlass().setId(new Random().nextLong());
//        Mockito.when(this.studentService.findById(Mockito.anyLong())).thenReturn(student);

        // 按接口规范，向url以规定的参数发起get请求。
        // 断言请求返回了正常的状态码
        String url = "/Student/" + id.toString() ;
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // 断言C层进行了数据转发（替身接收的参数值符合预期）
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(this.studentService).findById(longArgumentCaptor.capture());
        Assertions.assertThat(longArgumentCaptor.getValue()).isEqualTo(id);

        // 断言返回的json数据符合前台要求
        DocumentContext documentContext  = JsonPath.parse(mvcResult.getResponse().getContentAsString(Charset.defaultCharset()));
        LinkedHashMap studentHashMap = documentContext.json();
        Assertions.assertThat(studentHashMap.get("id")).isEqualTo(Integer.valueOf(id.toString()));
        Assertions.assertThat(studentHashMap.get("sno")).isEqualTo(student.getSno());
        Assertions.assertThat(studentHashMap.get("name")).isEqualTo(student.getName());
        LinkedHashMap klassHashMap = (LinkedHashMap) studentHashMap.get("klass");
        Assertions.assertThat(klassHashMap.get("id")).isEqualTo(Integer.valueOf(student.getKlass().getId().toString()));
    }


    @Test
    public void update() throws Exception {
        // 准备传入参数的数据
        Long id = new Random().nextLong();

        // 准备服务层替身被调用后的返回数据
        Student mockResult = new Student();
        mockResult.setId(id);
        mockResult.setName(RandomString.make(6));
        mockResult.setSno(RandomString.make(4));
        mockResult.setKlass(new Klass());
        mockResult.getKlass().setId(new Random().nextLong());
        mockResult.getKlass().setName(RandomString.make(10));
        Mockito.when(this.studentService.update(Mockito.anyLong(), Mockito.any(Student.class))).thenReturn(mockResult);

        JSONObject studentJsonObject = new JSONObject();
        JSONObject klassJsonObject = new JSONObject();

        studentJsonObject.put("sno", RandomString.make(4));
        studentJsonObject.put("name", RandomString.make(6));
        klassJsonObject.put("id", new Random().nextLong());
        studentJsonObject.put("klass", klassJsonObject);

        // 按接口规范发起请求，断言状态码正常，接收的数据符合预期
        String url = "/Student/" + id.toString();
        this.mockMvc
                .perform(MockMvcRequestBuilders.put(url)
                        .content(studentJsonObject.toString())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("sno").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("name").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("klass.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("klass.name").exists())
        ;

        // 断言C层进行了数据转发（替身接收的参数值符合预期)
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

        Mockito.verify(this.studentService).update(longArgumentCaptor.capture(), studentArgumentCaptor.capture());
        Assertions.assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        Student resultStudent = studentArgumentCaptor.getValue();
        Assertions.assertThat(resultStudent.getSno()).isEqualTo(studentJsonObject.get("sno"));
        Assertions.assertThat(resultStudent.getName()).isEqualTo(studentJsonObject.get("name"));
        Assertions.assertThat(resultStudent.getKlass().getId()).isEqualTo(klassJsonObject.get("id"));
        Assertions.assertThat(resultStudent.getKlass().getName()).isEqualTo(klassJsonObject.get("name"));
    }

    @Test
    @Transactional
    public void page1() {
        Teacher teacher = new Teacher();

    }

}
