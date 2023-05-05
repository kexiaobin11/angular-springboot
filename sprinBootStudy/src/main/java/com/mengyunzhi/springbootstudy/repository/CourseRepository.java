package com.mengyunzhi.springbootstudy.repository;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Student;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import com.mengyunzhi.springbootstudy.repository.specs.CourseSpecs;
import com.sun.istack.NotNull;
import org.junit.Assert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long>,  JpaSpecificationExecutor {
    /**
     * 课程名称是否存在
     * @param name 课程名称
     * @return true 存在
     */
    boolean existsByName(String name);

   default Page<Course> findAll(String name, Teacher teacher, @NotNull Pageable pageable) {
       Assert.assertNotNull(pageable.toString(), "传入的pageable不能为null");
       Specification<Course> specification = CourseSpecs.belongToTeacher(teacher)
               .and(CourseSpecs.containingName(name));
       return this.findAll(specification, pageable);
   };
}