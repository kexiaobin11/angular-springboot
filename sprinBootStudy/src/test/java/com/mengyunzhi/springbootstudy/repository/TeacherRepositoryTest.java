package com.mengyunzhi.springbootstudy.repository;

import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.service.TeacherServiceImpl;
import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpServletRequest;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TeacherRepositoryTest {
    private HttpServletRequest httpServletRequest;
    private TeacherServiceImpl teacherService;
    private TeacherRepository teacherRepository;
    @Before
    public void before() {
        this.teacherRepository = Mockito.mock(TeacherRepository.class);
        this.httpServletRequest = Mockito.mock(HttpServletRequest.class);
        TeacherServiceImpl teacherService = new TeacherServiceImpl(this.teacherRepository, this.httpServletRequest);
        this.teacherService = Mockito.spy(teacherService);
    }
    @Test
    public void findByUsername() {
        String password = RandomString.make(6);
        String username = RandomString.make(6);
        Teacher teacher = new Teacher();
        this.teacherRepository.save(teacher);
        Mockito.when(this.teacherRepository.findByUsername(username)).thenReturn(teacher);
        Mockito.doReturn(true).when(this.teacherService).validatePassword(teacher, password);
        boolean result = this.teacherService.login(username, password);

        Assert.assertTrue(result);
        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(this.teacherRepository).findByUsername(argumentCaptor.capture());
        Assert.assertEquals(argumentCaptor.getValue(), username);
    }

    @Test
    public void validatePassword() {
        Teacher teacher = new Teacher();
        String password = RandomString.make(6);
        teacher.setPassword(password);
        Assert.assertTrue(this.teacherService.validatePassword(teacher, password));
        Assert.assertFalse(
                this.teacherService.validatePassword(null, password)
        );
        Assert.assertFalse(this.teacherService.validatePassword(teacher, null));
        teacher.setPassword(RandomString.make(6));
        Assert.assertFalse(this.teacherService.validatePassword(teacher, password));
    }
}
