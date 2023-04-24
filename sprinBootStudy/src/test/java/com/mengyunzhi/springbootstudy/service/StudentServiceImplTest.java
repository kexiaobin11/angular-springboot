package com.mengyunzhi.springbootstudy.service;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Student;
import com.mengyunzhi.springbootstudy.repository.StudentRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import java.nio.charset.Charset;
import java.util.*;


@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class StudentServiceImplTest {
    private static Logger logger = LoggerFactory.getLogger(StudentServiceImplTest.class);

    @MockBean
    StudentRepository studentRepository;

    @Autowired
    StudentService studentService;

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    MockMvc mockMvc;

    /**
     * 保存
     * 1. 模拟输入、输出
     * 2. 调用测试方法
     * 3. 断言数据转发与输出
     */
    @Test
    public void save() {
        StudentRepository studentRepository = Mockito.mock(StudentRepository.class);
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        configurableApplicationContext.getBeanFactory().registerSingleton("StudentRepository", studentRepository);

        Student passStudent = new Student();
        Student mockReturnStudent = new Student();
        Mockito.when(studentRepository.save(Mockito.any(Student.class)))
                .thenReturn(mockReturnStudent);

        Student returnStudent = this.studentService.save(passStudent);
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        Mockito.verify(studentRepository).save(studentArgumentCaptor.capture());

        Assertions.assertThat(studentArgumentCaptor.getValue()).isEqualTo(passStudent);
        Assertions.assertThat(returnStudent).isEqualTo(mockReturnStudent);
    }

    /**
     * 分页查询
     * 1. 模拟输入、输出、调用studentRepository
     * 2. 调用测试方法
     * 3. 断言输入与输出与模拟值相符
     */
    @Test
    public void findAll() {
        Pageable mockInPageable = PageRequest.of(1, 20);
        List<Student> mockStudents = Arrays.asList(new Student());
        Page<Student> mockOutStudentPage = new PageImpl<Student>(
                mockStudents,
                PageRequest.of(1, 20),
                21);
        Mockito.when(this.studentRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(mockOutStudentPage);

        Page<Student> studentPage = this.studentService.findAll(mockInPageable);

        Assertions.assertThat(studentPage).isEqualTo(mockOutStudentPage);
        ArgumentCaptor<Pageable> pageableArgumentCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(this.studentRepository).findAll(pageableArgumentCaptor.capture());
        Assertions.assertThat(pageableArgumentCaptor.getValue()).isEqualTo(mockInPageable);
    }

    @Test
    public void findAllSpecs() {
        /* 参数初始化 */
        String name = "hello";
        String sno = "032282";
        Long klassId = 1L;
        Pageable pageable = PageRequest.of(0, 2);
        List<Student> students = Arrays.asList();
        Page<Student> mockStudentPage = new PageImpl<>(students, pageable, 100L);

        /* 设置模拟返回值 */
        Mockito.when(this.studentRepository
                        .findAll(Mockito.eq(name),
                                Mockito.eq(sno),
                                Mockito.any(Klass.class),
                                Mockito.eq(pageable)))
                .thenReturn(mockStudentPage);

        /* 调用测试方法，获取返回值并断言与预期相同 */
        Page<Student> returnStudentPage = this.studentService.findAll(name, sno, klassId, pageable);
        Assertions.assertThat(returnStudentPage).isEqualTo(mockStudentPage);

        /* 获取M层调用studentRepository的findAll方法时klass的参数值，并进行断言 */
        ArgumentCaptor<Klass> klassArgumentCaptor = ArgumentCaptor.forClass(Klass.class);
        Mockito.verify(this.studentRepository).findAll(Mockito.eq(name), Mockito.eq(sno), klassArgumentCaptor.capture(), Mockito.eq(pageable));
        Assertions.assertThat(klassArgumentCaptor.getValue().getId()).isEqualTo(klassId);

        Mockito.verify(this.studentRepository).findAll(Mockito.any(String.class), Mockito.any(String.class), klassArgumentCaptor.capture(), Mockito.any(Pageable.class));
        Assertions.assertThat(klassArgumentCaptor.getValue().getId()).isEqualTo(klassId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllSpecsNullValidate() {
        try {
            this.studentService.findAll(null, null, null, null);
        } catch (Exception e) {
            Assertions.assertThat(e.getMessage()).isEqualTo("Pageable不能为null");
            throw e;
        }
    }

    @Test
    public void findById() throws Exception {
        // 准备调用时的参数及返回值
        Long id = new Random().nextLong();
        Student student = new Student();
        student.setId(id);
        student.setSno(new RandomString(6).nextString());
        student.setName(new RandomString(8).nextString());
        student.setKlass(new Klass());
        student.getKlass().setId(new Random().nextLong());
        Mockito.when(this.studentService.findById(Mockito.anyLong())).thenReturn(student);
        String url = "/Student/" + id.toString();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(this.studentService).findById(longArgumentCaptor.capture());
        Assertions.assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
        DocumentContext documentContext = JsonPath.parse(mvcResult.getResponse().getContentAsString(Charset.defaultCharset()));
        LinkedHashMap studentHashMap = documentContext.json();
        Assertions.assertThat(studentHashMap.get("id")).isEqualTo(Integer.valueOf(id.toString()));
        Assertions.assertThat(studentHashMap.get("sno")).isEqualTo(student.getSno());
        Assertions.assertThat(studentHashMap.get("name")).isEqualTo(student.getName());
        LinkedHashMap klassHashMap = (LinkedHashMap) studentHashMap.get("klass");
        Assertions.assertThat(klassHashMap.get("id")).isEqualTo(Integer.valueOf(student.getKlass().getId().toString()));

    }

    @Test
    public void getById() throws Exception {
        Long id = new Random().nextLong();

        // 准备服务层替身被调用后的返回数据
        Student student = new Student();
        student.setId(id);
        student.setSno(new RandomString(6).nextString());
        student.setName(new RandomString(8).nextString());
        student.setKlass(new Klass());
        student.getKlass().setId(new Random().nextLong());
        Mockito.when(this.studentService.findById(Mockito.anyLong()))
                .thenReturn(student);
        String url = "/Student/" + id.toString();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(id))
                .andExpect(MockMvcResultMatchers.jsonPath("sno").value(student.getSno()))
                .andExpect(MockMvcResultMatchers.jsonPath("name").value(student.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("klass.id").value(student.getKlass().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("klass.name").value(student.getKlass().getName()))
                .andReturn();
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(this.studentService).findById(longArgumentCaptor.capture());
        Assertions.assertThat(longArgumentCaptor.getValue()).isEqualTo(id);
    }
    @Test
    public void deleteById() {
        Long id = new Random().nextLong();

        // studentRepository.deleteById方法的返回值类型为void。
        // Mockito已默认为返回值为void默认生了返回值，无需对此替身单元做设置

        // 调用方法
        this.studentService.deleteById(id);

        // 预测以期望的参数值调用了期望的方法
        ArgumentCaptor<Long> longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(this.studentRepository).deleteById(longArgumentCaptor.capture());
        Assert.assertEquals(longArgumentCaptor.getValue(), id);
    }
}