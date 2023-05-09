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

import java.util.Collection;

public interface CourseRepository extends PagingAndSortingRepository<Course, Long>,  JpaSpecificationExecutor {
    /**
     * 课程名称是否存在
     * @param name 课程名称
     * @return true 存在
     */
    boolean existsByName(String name);

   default Page<Course> findAll(String name,Long klassId, Long teacherId, @NotNull Pageable pageable) {
       Assert.assertNotNull(pageable.toString(), "传入的pageable不能为null");
       Specification<Course> specification;
       if (klassId == null && teacherId == null) {
          specification = CourseSpecs.containingName(name);
       } else if (klassId == null && teacherId != null)  {
           specification = CourseSpecs.belongToTeacher(teacherId).and(CourseSpecs.containingName(name));
       } else if(klassId != null && teacherId == null) {
          specification = CourseSpecs.containingName(name).and(CourseSpecs.queryKlass(klassId));
       } else {
           specification = CourseSpecs.belongToTeacher(teacherId).and(CourseSpecs.containingName(name)).and(CourseSpecs.queryKlass(klassId));
       }
       return this.findAll(specification, pageable);
   };
    void deleteByIdIn(Collection<Long> ids);
}