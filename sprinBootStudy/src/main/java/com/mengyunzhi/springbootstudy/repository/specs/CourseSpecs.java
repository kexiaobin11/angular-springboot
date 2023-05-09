package com.mengyunzhi.springbootstudy.repository.specs;

import com.mengyunzhi.springbootstudy.entity.Course;
import com.mengyunzhi.springbootstudy.entity.Klass;
import com.mengyunzhi.springbootstudy.entity.Student;
import com.mengyunzhi.springbootstudy.entity.Teacher;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

public class CourseSpecs {

    /*
    * @params name 课程名
    * */
    public static Specification<Course> containingName (String name) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
              return criteriaBuilder.like(root.get("name").as(String.class), String.format("%%%s%%", name));
            }
        };
    }
    public static Specification<Course> belongToTeacher(Long teacherId) {
     return new Specification<Course>() {
         @Override
         public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
             return criteriaBuilder.equal(root.get("teacher").get("id").as(Long.class), teacherId);
         }
     };
    }
    public static Specification<Course> queryKlass(Long klassId) {
        return new Specification<Course>() {
            @Override
            public Predicate toPredicate(Root<Course> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                Join<Course, Klass> courseklassJoin = root.join("klasses", JoinType.INNER);
                Predicate predicate = criteriaBuilder.equal(courseklassJoin.get("id"), klassId);
                return predicate;
            };
        };
    }
}
